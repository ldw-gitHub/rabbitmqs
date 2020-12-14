package com.framemark.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @author: liudawei
 * @date: 2020/11/2 18:13
 */
public class Girlfriend implements Serializable {

    private static final long serialVersionUID = -1L;

    private String name;
    private Integer age;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Girlfriend{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", date=" + date +
                '}';
    }
}
