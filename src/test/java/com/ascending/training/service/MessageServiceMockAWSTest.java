/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.ascending.training.init.AppInitializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/* Static imports allow you to call static members, i.e., methods and fields of a class directly without specifying the class. */

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
public class MessageServiceMockAWSTest {
    @Autowired
    @Spy
    private Logger logger;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private AmazonSQS amazonSQS;
    @InjectMocks
    private MessageService messageService;

    private String queueName = "training_queue_ascending_com";
    private String fakeQueueUrl = "www.fakeQueueUrl.com/abc/123/fake";
    private String msg = "This is a message for test";
    private List<Message> messages;

    @Before
    public void setUp() {
        logger.info(">>>>>>>>>> Start testing...");
        messages = new ArrayList();
        messages.add(new Message());
        //Mocks are initialized before each test method
        MockitoAnnotations.initMocks(this);
        when(amazonSQS.getQueueUrl(anyString()).getQueueUrl()).thenReturn(fakeQueueUrl);
        when(amazonSQS.receiveMessage(any(ReceiveMessageRequest.class)).getMessages()).thenReturn(messages);
        when(amazonSQS.createQueue(any(CreateQueueRequest.class)).getQueueUrl()).thenReturn(fakeQueueUrl);
    }

    @After
    public void tearDown() {
        logger.info(">>>>>>>>>> End test");
    }

    @Test
    public void getQueueUrl() {
        String queueUrl = messageService.getQueueUrl(queueName);
        assertEquals(queueUrl, fakeQueueUrl);

        //Verify the method getQueueUrl is called 2 times
        verify(amazonSQS, times(2)).getQueueUrl(anyString());

        //The logger is spied object, so, the real methods are called,
        //The purpose of spy an object is to assert the result (state verification) returned by a called method
        //or verify the method is called (behavior verification)
        verify(logger, atLeast(1)).info(anyString());
    }

    @Test
    public void sendMessage() {
        messageService.sendMessage(queueName, msg);
        verify(amazonSQS, times(1)).sendMessage(any());
    }

    @Test
    public void creatQueue() {
        when(amazonSQS.getQueueUrl(anyString()).getQueueUrl()).thenThrow(new QueueDoesNotExistException("The queue doesn't exist."));
        String queueUrl = messageService.createQueue(queueName);
        assertEquals(fakeQueueUrl, queueUrl);
        verify(amazonSQS, timeout(10).times(1)).createQueue(any(CreateQueueRequest.class));
    }

    @Test
    public void getMessages() {
        List<Message> messages = messageService.getMessages(queueName);
        assertNotNull(messages);
        assertEquals(1, messages.size());

        //verify the method receiveMessage is called one times
        verify(amazonSQS, times(1)).receiveMessage(any(ReceiveMessageRequest.class));
    }
}

/*
 *   doReturn/when VS when/thenReturn:
 *      1. doReturn/when and when/thenReturn are same for mocked object. None of them call the actual method
 *      2. doReturn/when and when/thenReturn have different behaviour for spied object.
 *         doReturn/when - it will not call real method on spied object
 *         when/thenReturn - it will call real method on spied object
 */