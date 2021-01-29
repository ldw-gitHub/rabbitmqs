package com.framemark.service.Impl;

import com.framemark.annotation.MyLog;
import com.framemark.constants.LogModularType;
import com.framemark.constants.LogType;
import com.framemark.model.LogModel;
import com.framemark.service.LogService;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author: liudawei
 * @date: 2020/12/16 14:31
 */
@Service
public class LogServiceImpl implements LogService {
    @Override
    @MyLog(logType = LogType.Constants.INERT_VALUE, logModular = LogModularType.Constants.LOG_VALUE)
    public void insertOne(LogModel logModel) throws Exception {
        Thread.sleep(2000L);

//        throw new Exception("测试错误！");
    }
}
