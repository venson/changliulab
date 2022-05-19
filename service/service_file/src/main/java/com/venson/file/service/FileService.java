package com.venson.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(String subDir,MultipartFile file);
}
