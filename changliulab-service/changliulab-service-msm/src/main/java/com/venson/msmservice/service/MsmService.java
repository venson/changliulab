package com.venson.msmservice.service;

public interface MsmService {
    boolean sendCode(String emailUrl, String code,String codeName,String mailPurpose, Integer expire);
}
