package com.framemark.model;

/**
 * @description
 * @author: liudawei
 * @date: 2020/12/16 14:33
 */
public class LogModel {

    public String logType;

    public LogModel(String logType) {
        this.logType = logType;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
