/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package org.liwei.training.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import org.liwei.training.constant.AppConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
/* SCOPE_SINGLETON is default scope, it can be omitted */
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MessageServiceImpl implements MessageService {
    //@Autowired
    private Logger logger;
    //@Autowired
    private AmazonSQS amazonSQS;

    /* Constructor injection*/
    @Autowired
    public MessageServiceImpl(Logger logger, AmazonSQS amazonSQS) {
        this.logger = logger;
        this.amazonSQS = amazonSQS;
    }

    @Override
    public String createQueue(String queueName) {
        String queueUrl = null;

        try {
            queueUrl = getQueueUrl(queueName);
        }
        catch (QueueDoesNotExistException e) {
            CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
            queueUrl = amazonSQS.createQueue(createQueueRequest).getQueueUrl();
        }

        logger.info(AppConstants.MSG_PREFIX + queueUrl);

        return queueUrl;
    }

    @Override
    public String getQueueUrl(String queueName) {
        GetQueueUrlResult getQueueUrlResult = amazonSQS.getQueueUrl(queueName);
        logger.info(AppConstants.MSG_PREFIX + "QueueUrl: " + getQueueUrlResult.getQueueUrl());
        return getQueueUrlResult.getQueueUrl();
    }

    @Override
    public void sendMessage(String queueName, String msg) {
        Map<String, MessageAttributeValue> messageAttributes = new HashMap();
        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").withStringValue("File URL in S3");
        messageAttributes.put("message", messageAttributeValue);
        String queueUrl = getQueueUrl(queueName);
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.withQueueUrl(queueUrl)
                          .withMessageBody(msg)
                          .withMessageAttributes(messageAttributes);
        amazonSQS.sendMessage(sendMessageRequest);
    }

    @Override
    public List<Message> getMessages(String queueName) {
        String queueUrl = getQueueUrl(queueName);
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
        return messages;
    }
}
