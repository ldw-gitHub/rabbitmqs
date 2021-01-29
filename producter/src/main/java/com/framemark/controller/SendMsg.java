package com.framemark.controller;

import com.framemark.annotation.LoginRequired;
import com.framemark.annotation.MyLog;
import com.framemark.config.RedisUtils;
import com.framemark.constants.LogModularType;
import com.framemark.constants.LogType;
import com.framemark.model.LogModel;
import com.framemark.product.SenderAck;
import com.framemark.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private LogService logService;

    /**
     * @description: mq发送测试
     * @author: liudawei
     * @date: 2020/12/14 15:26
     * @param: msg
     * @return: java.lang.String
     */
    @MyLog(logType = LogType.Constants.INERT_VALUE, logModular = LogModularType.Constants.LOG_VALUE)
    @LoginRequired
    @PostMapping("/sendQueue1Msg")
    public String sendQueue1Msg(String msg) throws IOException, InterruptedException {
        senderAck.send(msg);
        senderAck.sendDesign(msg);
        Thread.sleep(new Random().nextInt(1000));
        return "success";
    }

    /**
     * @description: redis锁测试
     * @author: liudawei
     * @date: 2020/12/14 15:26
     * @param:
     * @return: int
     */
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


//    @MyLog(logType = LogType.Constants.INERT_VALUE, logModular = LogModularType.Constants.LOG_VALUE)
    @PostMapping("/logAspect")
    public String logAspect(@RequestParam(name = "file") MultipartFile file,
                            Long id, String filename) throws Exception {
        logService.insertOne(new LogModel(LogType.Constants.INERT_VALUE));
        return "success";
    }


}
