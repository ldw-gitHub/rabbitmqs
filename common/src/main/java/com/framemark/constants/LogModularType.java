package com.framemark.constants;

/**
 * @description 日志操作模块
 * @author: liudawei
 * @date: 2020/12/16 14:53
 */
public enum LogModularType {

    LOG("日志模块", Constants.LOG_VALUE);

    public static class Constants {
        public static final String LOG_VALUE = "log_modular";
    }

    private String value;
    private String name;

    LogModularType(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public static boolean isContain(String value) {

        LogModularType[] types = LogModularType.values();
        for (LogModularType e : types) {
            if (e.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(String value) {
        LogModularType[] types = LogModularType.values();
        for (LogModularType e : types) {
            if (e.getValue().equals(value)) {
                return e.getName();
            }
        }
        return "";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
