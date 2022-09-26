package com.venson.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.venson.oss.service.OssService;
import com.venson.oss.utils.OssUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Service
@Slf4j
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(String path, MultipartFile file) {
        String fileName = file.getOriginalFilename();
//        fileName = URLEncoder.encode(fileName,StandardCharsets.UTF_8);
        String fullPath = path + "/" + fileName;
        OSS ossClient = new OSSClientBuilder().build(OssUtils.END_POINT, OssUtils.ACCESS_KEY_ID, OssUtils.ACCESS_KEY_SECRET);

        try {
            InputStream inputStream = file.getInputStream();
            log.info("try upload file:" + fullPath);
            ossClient.putObject(OssUtils.BUCKET_NAME, fullPath, inputStream);
            String manualUrl =  "https://" + OssUtils.BUCKET_NAME +"." + OssUtils.END_POINT+ "/" + fullPath;

            URL url = ossClient.generatePresignedUrl(OssUtils.BUCKET_NAME,fullPath,
                    new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7));
            ossClient.shutdown();

            String s;
            String urlString =  url.getProtocol()
                    + ':'
                    + ((s = url.getAuthority()) != null && !s.isEmpty()
                    ? "//" + s : "")
                    + ((s = url.getPath()) != null ? s : "");
            log.info("successful uploaded file:" + urlString);

            return urlString;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("failed to upload file:" + path+"/"+file.getOriginalFilename());
        }
        finally {
            if(ossClient!=null)
            ossClient.shutdown();
        }
        return null;
    }
}
