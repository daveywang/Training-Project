/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ascending.training.constant.AppConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
/* SCOPE_SINGLETON is default scope, it can be omitted */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileServiceImpl implements FileService {
    //@Autowired
    private Logger logger;
    //@Autowired
    private AmazonS3 amazonS3;

    /* Constructor injection*/
    @Autowired
    public FileServiceImpl(Logger logger, AmazonS3 amazonS3) {
        this.logger = logger;
        this.amazonS3 = amazonS3;
    }

    /*
     * MultipartFile is a representation of an uploaded file received in a multipart request.
     * multipart request is the request that one or more different sets of data are combined in the request body
     *
     */
    @Override
    public String uploadFile(String bucketName, MultipartFile file) {
        String fileUrl = null;

        try {
            if (amazonS3.doesObjectExist(bucketName, file.getOriginalFilename())) {
                logger.info(String.format(AppConstants.MSG_PREFIX + "The file '%s' exists in the bucket %s", file.getOriginalFilename(), bucketName));
                return fileUrl;
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), objectMetadata);
            fileUrl = getFileUrl(bucketName, file.getOriginalFilename());
            logger.info(String.format(AppConstants.MSG_PREFIX + "The file name=%s, size=%d was uploaded to bucket %s", file.getOriginalFilename(), file.getSize(), bucketName));
        }
        catch (Exception e) {
            logger.error(AppConstants.MSG_PREFIX + e.getMessage());
        }

        return fileUrl;
    }

    @Override
    public String getFileUrl(String bucketName, String fileName) {
        LocalDateTime expiration = LocalDateTime.now().plusDays(1);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName);
        generatePresignedUrlRequest.withMethod(HttpMethod.GET);
        generatePresignedUrlRequest.withExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)));

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

    @Override
    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) amazonS3.createBucket(bucketName);
    }

    @Override
    public boolean saveFile(MultipartFile multipartFile, String filePath) {
        boolean isSuccess = false;

        try {
            File directory = new File(filePath);
            if (!directory.exists()) directory.mkdir();
            Path path = Paths.get(filePath, multipartFile.getOriginalFilename());
            multipartFile.transferTo(path);
            isSuccess = true;
            logger.info(String.format(AppConstants.MSG_PREFIX + "The file %s is saved in the foldr %s", multipartFile.getOriginalFilename(), filePath));
        }
        catch(Exception e) {
            logger.error(AppConstants.MSG_PREFIX + e.getMessage());
        }

        return isSuccess;
    }
}
