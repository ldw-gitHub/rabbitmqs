package com.framemark.constants;

/**
 * @description 日志操作类型
 * @author: liudawei
 * @date: 2020/12/16 14:53
 */
public enum LogType {

    INSERT("新增", Constants.INERT_VALUE),
    UPDATE("更新", Constants.UPDATE_VALUE),
    DELETE("删除", Constants.DELETE_VALUE),
    QUERY("查询", Constants.QUERY_VALUE);

    public static class Constants {
        public static final String INERT_VALUE = "insert";
        public static final String UPDATE_VALUE = "update";
        public static final String DELETE_VALUE = "delete";
        public static final String QUERY_VALUE = "QUERY";
    }

    private String value;
    private String name;

    LogType(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public static boolean isContain(String value) {

        LogType[] types = LogType.values();
        for (LogType e : types) {
            if (e.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String getNameByValue(String value) {
        LogType[] types = LogType.values();
        for (LogType e : types) {
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
