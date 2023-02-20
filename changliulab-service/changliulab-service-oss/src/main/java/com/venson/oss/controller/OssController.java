package com.venson.oss.controller;

import com.venson.commonutils.Result;
import com.venson.oss.entity.OssAuth;
import com.venson.oss.entity.OssResult;
import com.venson.oss.service.OssService;
import com.venson.security.entity.bo.UserContextInfoBO;
import com.venson.security.utils.ContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/eduoss/admin/fileoss")
@Slf4j
public class OssController {

    @Autowired
    private OssService ossService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<OssResult> uploadOssFile(@RequestParam String path,@RequestPart() MultipartFile file){
        String url = ossService.uploadFileAvatar(path,file);
       return Result.success(new OssResult(url));
    }
    @GetMapping("auth")
    @PreAuthorize("hasAnyAuthority('activity.edit', 'course.edit')")
    public Result<OssAuth> getUploadAuth(@RequestParam(required = false) String path){
        UserContextInfoBO userContext = ContextUtils.getUserContext();
        if(userContext ==null || userContext.getId() ==null){
            return Result.error();
        }
        OssAuth auth = ossService.getUploadAuth(path);
        return Result.success(auth);
    }
}
