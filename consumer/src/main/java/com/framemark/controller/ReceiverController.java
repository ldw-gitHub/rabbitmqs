package com.framemark.controller;

import com.framemark.consumer.TopicDesignReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @description
 * @author: liudawei
 * @date: 2020/11/9 16:59
 */
@RestController
public class ReceiverController {

    @Autowired
    TopicDesignReceiver topicDesignReceiver;

    @PostMapping("/receiverMsg")
    public String sendQueue1Msg(String clientId) throws IOException {
        topicDesignReceiver.receiverMsg(clientId);
        return "success";
    }
}
