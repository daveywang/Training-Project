/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.service;

import com.ascending.training.constant.AppConstants;
import com.ascending.training.init.AppInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class FileServiceTest {
    @Autowired
    private Logger logger;
    @Autowired
    private FileService fileService;
    private String bucketName = "training-bucket.ascending.com";
    private String fileName = "test.txt";
    private MultipartFile multipartFile;
    private String path;

    @Before
    public void setUp() throws IOException {
        logger.info(AppConstants.MSG_PREFIX + "Start testing...");

        File file = new File("/Users/liweiwang/ascending/lecture/README.md");
        FileInputStream input = new FileInputStream(file);
        multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", input);
        path = System.getProperty("user.dir") + File.separator + "temp";
    }

    @After
    public void tearDown() {
        logger.info(AppConstants.MSG_PREFIX + "End test");
    }

    @Test
    public void uploadFile() throws IOException{
        String fileUrl = fileService.uploadFile(bucketName, multipartFile);
        Assert.assertNull(fileUrl);
    }

    @Test
    public void saveFile() throws IOException, FileNotFoundException {
        boolean isSuccess = fileService.saveFile(multipartFile, path);
        Assert.assertTrue(isSuccess);
    }
}

/*
 *   doReturn/when VS when/thenReturn:
 *      1. doReturn/when and when/thenReturn are same for mocked object. None of them call the actual method
 *      2. doReturn/when and when/thenReturn have different behaviour for spied object.
 *         doReturn/when - it will not call real method on spied object
 *         when/thenReturn - it will call real method on spied object
 */