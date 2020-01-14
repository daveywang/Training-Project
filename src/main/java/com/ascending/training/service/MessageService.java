/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.service;

import com.amazonaws.services.sqs.model.Message;

import java.util.List;

public interface MessageService {
    String createQueue(String queueName);
    String getQueueUrl(String queueName);
    void sendMessage(String queueName, String msg);
    List<Message> getMessages(String queueName);
}
