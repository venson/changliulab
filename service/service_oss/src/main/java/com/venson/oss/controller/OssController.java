package com.venson.oss.controller;

import com.venson.commonutils.RMessage;
import com.venson.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
@Slf4j
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public RMessage uploadOssFile(@RequestPart("file") MultipartFile file){
        log.info("start upload");
        String url = ossService.uploadFileAvatar(file);
       return RMessage.ok().data("url", url);
    }
}
