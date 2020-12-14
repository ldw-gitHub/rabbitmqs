package com.framemark.controller;

import com.framemark.config.RedisUtils;
import com.framemark.product.SenderAck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Random;

/**
 * @description
 * @author: liudawei
 * @date: 2020/11/2 18:00
 */
@RestController
@Slf4j
public class SendMsg {

    @Autowired
    private SenderAck senderAck;
    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/sendQueue1Msg")
    public String sendQueue1Msg(String msg) throws IOException, InterruptedException {
        senderAck.send(msg);
        senderAck.sendDesign(msg);
        Thread.sleep(new Random().nextInt(1000));
        return "success";
    }

    @PostMapping("/redisLock")
    public int redisLock() throws IOException, InterruptedException {
        //并发不加锁
        boolean redLock = redisUtils.getDistributeLock("redLock", 5L);
        int number = Integer.parseInt(redisUtils.get("redLock") + "");

        redisUtils.set("redLock", (number - 1) + "");

        log.info("库存剩余 ------- " + redisUtils.get("redLock"));
        Thread.sleep(new Random().nextInt(1000));
        return number - 1;
    }


}
