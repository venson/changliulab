package com.venson.msmservice.service;

public interface MsmService {
    boolean sendCode(String emailUrl, String code,String type);
}
