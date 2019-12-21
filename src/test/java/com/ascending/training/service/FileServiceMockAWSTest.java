/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.ascending.training.init.AppInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/* Static imports allow you to call static members, i.e., methods and fields of a class directly without specifying the class. */

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class FileServiceMockAWSTest {
    /*  FileService has two injected objects logger and amazonS3, so we have to mock these two objects here by using @Mock
     *  then use @InjectMocks inject these two objects into FileService
     *  if, the injected object is not mocked, then the object in the FileService will be null.
     *  for example, if no logger is mocked, the logger in FileService is null.
     *  We can also use constructor injection to inject mocked and real objects into the FileService
     */

    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private AmazonS3 amazonS3;
    @Autowired
    @Spy
    private Logger logger;  //autowired the logger and inject it into the object fileService
    @InjectMocks
    private FileServiceImpl fileService;  //fileService is not mocked object
    private String bucketName = "training_queue_ascending_com";
    private String fileName = "test.txt";
    private URL fakeFileUrl;
    @Mock
    private MultipartFile multipartFile;
    //private String path;

    @Before
    public void setUp() throws MalformedURLException, FileNotFoundException, IOException {
        logger.info(">>>>>>>>>> Start testing...");

        //Mocks are initialized before each test method
        MockitoAnnotations.initMocks(this);

        fakeFileUrl = new URL("http://www.fakeQueueUrl.com/abc/123/fake");
        //File file = new File("/Users/liweiwang/ascending/lecture/README.md");
        //FileInputStream input = new FileInputStream(file);
        //multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
        //path = System.getProperty("user.dir") + File.separator + "temp";

        //Stubbing for the methods in the object multipartFile
        when(multipartFile.getOriginalFilename()).thenReturn("anyFileName");
        when(multipartFile.getContentType()).thenReturn("Application");
        when(multipartFile.getSize()).thenReturn(9999L);
        when(multipartFile.getInputStream()).thenReturn(mock(InputStream.class));


        //Stubbing for the methods doesObjectExist and generatePresignedUrl in the object amazonS3
        when(amazonS3.doesObjectExist(anyString(), anyString())).thenReturn(false);
        when(amazonS3.generatePresignedUrl(any())).thenReturn(fakeFileUrl);
    }

    @After
    public void tearDown() {
        logger.info(">>>>>>>>>> End test");
    }

    @Test
    public void getFileUrl() {
        String fileUrl = fileService.getFileUrl(bucketName, fileName);
        assertEquals(fileUrl, fakeFileUrl.toString());
        verify(amazonS3, times(1)).generatePresignedUrl(any());
    }

    @Test
    public void uploadFile() throws IOException{
        fileService.uploadFile(bucketName, multipartFile);
        verify(amazonS3, times(1)).doesObjectExist(anyString(), anyString());
        verify(amazonS3, times(1)).putObject(anyString(), anyString(), any(), any());
    }

    @Test
    public void saveFile() throws IOException, FileNotFoundException {
        //Dummies
        MultipartFile multipartFile = new MockMultipartFile(" ", new byte[1]);
        String path = " ";

        //Annotation @Mock can only be used for calls variables
        //create mocked object fshttps://tomcat.apache.org/download-80.cgi
        FileServiceImpl fs = Mockito.mock(FileServiceImpl.class);

        //Stubbing
        //when(fs.saveFile(any(), anyString())).thenReturn(true);
        doReturn(true).when(fs).saveFile(any(), anyString());

        //Exercise - call method
        //Use dummies as parameters
        boolean isSuccess = fs.saveFile(multipartFile, path);

        //Verify state
        Assert.assertTrue(isSuccess);

        //Verify behavior
        verify(fs, times(1)).saveFile(any(), anyString());
    }

}

/*
 *   doReturn/when VS when/thenReturn:
 *      1. doReturn/when and when/thenReturn are same for mocked object. None of them call the actual method
 *      2. doReturn/when and when/thenReturn have different behaviour for spied object.
 *         doReturn/when - it will not call real method on spied object
 *         when/thenReturn - it will call real method on spied object
 */