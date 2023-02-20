package com.venson.msmservice.service;

import com.venson.msmservice.entity.enums.SendStatus;

public interface MsmFrontService {
    SendStatus getSecurityCode(String emailUrl);
}
