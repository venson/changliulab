package com.venson.msmservice.controller;

import com.venson.commonutils.Result;
import com.venson.msmservice.entity.enums.SendStatus;
import com.venson.msmservice.service.MsmFrontService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/edumsm/front/msm")
@Slf4j
public class FrontMsmController {
    private final MsmFrontService msmFrontService;

    @Autowired
    public FrontMsmController(MsmFrontService msmFrontService) {
        this.msmFrontService = msmFrontService;
    }


    @PostMapping(value = "securityCode")
    public Result<String> sendEmail(@RequestBody String emailUrl){
        SendStatus status = msmFrontService.getSecurityCode(emailUrl);
        switch (status){
            case SUCCESS -> {
                return Result.success();
            }
            case TOO_FREQUENTLY -> {
                return Result.error("Please try after 5 minutes");
            }
            case TOO_MANY_ATTEMPTS -> {
                return Result.error("3 attempts per day, Please try tomorrow");
            }
            default -> {
                return Result.error();
            }
        }
    }
}
