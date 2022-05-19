package com.venson.file.controller;

import com.venson.commonutils.RMessage;
import com.venson.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "uploadBanner", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RMessage uploadBanner(@RequestPart("file") final MultipartFile file){
        if(file.isEmpty()){
            return RMessage.error().message("文件为空");
        }
        String path = fileService.uploadFile("banner",file);
        return RMessage.ok().data("path",path);
    }

    @PostMapping(value = "uploadPhoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RMessage uploadPhoto(@RequestPart("file") final MultipartFile file){
        if(file.isEmpty()){
            return RMessage.error().message("文件为空");
        }
        String path = fileService.uploadFile("photo",file);
        return RMessage.ok().data("path",path);
    }
}
