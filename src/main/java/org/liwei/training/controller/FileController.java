/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.controller;

import org.liwei.training.constant.AppConstants;
import org.liwei.training.service.FileService;
import org.liwei.training.service.FileServiceImpl;
import org.liwei.training.service.MessageService;
import org.liwei.training.service.MessageServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@RestController
@RequestMapping(value = {"/files"})
public class FileController {
    /* Inject value from vm options */
    @Value("${aws.queue.name}")
    private String queueName;
    @Value("${file.download.dir}")
    private String fileDownloadDir;
    @Value( "${base.path}" )
    private String basePath;

    private Logger logger;
    private FileService fileService;
    private MessageService messageService;

    @Autowired
    public FileController(Logger logger, FileServiceImpl fileService, MessageServiceImpl messageService) {
        this.logger = logger;
        this.fileService = fileService;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/{bucketName}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@PathVariable String bucketName, @RequestParam("file") MultipartFile file) {
        String msg = String.format("The file name=%s, size=%d could not be uploaded.", file.getOriginalFilename(), file.getSize());
        ResponseEntity responseEntity = ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).body(msg);

        try {
            String path = System.getProperty("user.dir") + File.separator + "temp";
            fileService.saveFile(file, path);

            String url = fileService.uploadFile(bucketName, file);

            if (url != null) {
                msg = String.format("The file name=%s, size=%d was uploaded, url=%s", file.getOriginalFilename(), file.getSize(), url);
                messageService.sendMessage(queueName, url);
                responseEntity = ResponseEntity.status(HttpServletResponse.SC_OK).body(msg);
            }

            logger.info(AppConstants.MSG_PREFIX + msg);
        }
        catch (Exception e) {
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).body(e.getMessage());
            logger.error(AppConstants.MSG_PREFIX + e.getMessage());
        }

        return responseEntity;
    }

    @RequestMapping(value = "/{fileName}", method = RequestMethod.GET, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = null;
        String msg = "The file doesn't exist.";
        ResponseEntity responseEntity;

        try {
            Path filePath = Paths.get(fileDownloadDir).toAbsolutePath().resolve(fileName).normalize();
            resource = new UrlResource(filePath.toUri());
            if(!resource.exists()) return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(msg);
            responseEntity = ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                             .body(resource);;

            msg = String.format("The file %s was downloaded", resource.getFilename());
            //Send message to SQS
            messageService.sendMessage(queueName, msg);
            logger.debug(AppConstants.MSG_PREFIX + msg);
        }
        catch (Exception ex) {
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(ex.getMessage());
            logger.debug(AppConstants.MSG_PREFIX + ex.getMessage());
        }

        return responseEntity;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFiles(@RequestParam String filePath, @RequestParam("files") MultipartFile[] files) {
        String path = basePath + File.separator + filePath;
        logger.info(AppConstants.MSG_PREFIX + path);

        if (files == null || (files.length == 0)) {
            String msg = String.format("Please select one or more files to upload");
            return ResponseEntity.status(HttpServletResponse.SC_NOT_ACCEPTABLE).body(msg);
        }

        if (!fileService.isPathExist(path)) {
            String msg = String.format("The path %s doesn't exist", path);
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(msg);
        }

        ArrayList<String> succeedFiles = new ArrayList();
        ArrayList<String> failedFiles = new ArrayList();
        ArrayList<String> existedFiles = new ArrayList();

        for (MultipartFile file : files) {
            try {
                if (!fileService.isFileExist(path + File.separator + file.getOriginalFilename())) {
                    fileService.saveFile(file, path);
                    succeedFiles.add(file.getOriginalFilename());
                }
                else {
                    existedFiles.add(file.getOriginalFilename());
                }
            }
            catch (Exception e) {
                failedFiles.add(file.getOriginalFilename());
                logger.error(AppConstants.MSG_PREFIX + e.getMessage());
            }
        }

        String msg1 = String.format("The files %s were uploaded to the path %s\n", succeedFiles, path);
        String msg2 = String.format("The files %s already exist in the path %s\n", existedFiles, path);
        String msg3 = String.format("The files %s were failed to upload the path %s\n", failedFiles, path);
        String msg = "";

        if (!succeedFiles.isEmpty()) msg += msg1;
        if (!existedFiles.isEmpty()) msg += msg2;
        if (!failedFiles.isEmpty()) msg += msg3;

        ResponseEntity responseEntity = ResponseEntity.status(HttpServletResponse.SC_OK).body(msg);
        return responseEntity;
    }
}
