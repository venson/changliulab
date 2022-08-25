package com.venson.commonutils;

public enum ResultCode {
    SUCCESS(20000),
    ERROR(20001);

    private final int value;

    ResultCode(final int value){
        this.value = value;
    }

    public int getValue(){return value;}
}
