package com.venson.commonutils;

public enum ResultCode {
    SUCCESS(20000,"operation successful"),
    ERROR(20001,"operation failed"),
    ILLEGAL_TOKEN(50008, "Not login"),
    OTHER_LOGIN(50012, "Account login on other device"),
    TOKEN_EXPIRE(50014, "Please re-login"),
    UNAUTHORIZED(50030, "Authority Failed");


    private final Integer value;
    private final String desc;

    ResultCode(final Integer value, final String desc){
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue(){return value;}

    public String getDesc(){return desc;}
}
