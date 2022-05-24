package com.venson.msmservice.controller;

import com.venson.commonutils.RMessage;
import com.venson.commonutils.RandomString;
import com.venson.msmservice.service.MsmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/edumsm/msm")
@Slf4j
public class MsmController {

    private final MsmService msmService;

    public MsmController(MsmService msmService) {
        this.msmService = msmService;
    }

    @PostMapping("sendEmail")
    public RMessage sendEmail(@RequestBody String emailUrl){
        log.info(emailUrl);
        String code = RandomString.randomCode();
        msmService.sendCode(emailUrl, code);
        log.info(code);

        return RMessage.ok();
    }
}
