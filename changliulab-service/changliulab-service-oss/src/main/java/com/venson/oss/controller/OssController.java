package com.venson.oss.controller;

import com.venson.commonutils.Result;
import com.venson.oss.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/eduoss/admin/fileoss")
@Slf4j
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result uploadOssFile(@RequestParam String path,@RequestPart() MultipartFile file){
        String url = ossService.uploadFileAvatar(path,file);
       return Result.success().data("url", url);
    }
}
