package com.venson.security.entity;

public enum UserType {
    MEMBER(1,"Lab member"),
    USER(2,"normal user");

    Integer value;
    String desc;
    UserType(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
