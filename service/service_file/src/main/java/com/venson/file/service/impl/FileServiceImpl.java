package com.venson.file.service.impl;

import com.venson.file.service.FileService;
import com.venson.file.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String subDir,MultipartFile file) {


        String originalFilename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String newFileName = Utils.FILE_ROOT_PATH + Utils.IMAGE_PATH +"/"+subDir + "/" + uuid +originalFilename;
        try {
            file.transferTo(new File(newFileName));
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
