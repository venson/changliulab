package com.venson.file.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file.upload")
@EnableConfigurationProperties(Utils.class)
public class Utils implements InitializingBean{

    private String fileRootPath;
    private String imagePath;
    private String videoPath;

    public static String FILE_ROOT_PATH;
    public static String IMAGE_PATH;
    public static String VIDEO_PATH;

    public void setFileRootPath(String fileRootPath){
        this.fileRootPath = fileRootPath;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }
    public void setVideoPath(String videoPath){
        this.videoPath = videoPath;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        FILE_ROOT_PATH = fileRootPath;
        IMAGE_PATH = imagePath;
        VIDEO_PATH = videoPath;

    }
}
