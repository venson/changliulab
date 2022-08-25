package com.venson.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.venson.oss.service.OssService;
import com.venson.oss.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String  endpoint = Utils.END_POINT;
        String  accessKeyId = Utils.ACCESS_KEY_ID;
        String bucketName = Utils.BUCKET_NAME;
        String accessKeySecret = Utils.ACCESS_KEY_SECRET;


        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName,file.getOriginalFilename(),inputStream);
            ossClient.shutdown();
            return "https://" + bucketName +"." + endpoint + "/" + file.getOriginalFilename();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
