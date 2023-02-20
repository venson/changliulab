package com.venson.oss.service;

import com.venson.oss.entity.OssAuth;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadFileAvatar(String path, MultipartFile file);


    OssAuth getUploadAuth(String dir);
}
