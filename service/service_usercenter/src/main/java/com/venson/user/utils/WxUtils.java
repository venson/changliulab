package com.venson.user.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "wx.open")
public class WxUtils implements InitializingBean {

    private Long appId;
    private String appSecret;
    private String redirectUrl;

    public static Long WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;

    public void setAppId(Long appId){
        this.appId = appId;
    }

    public void setAppSecret(String appSecret){
        this.appSecret = appSecret;
    }
    public void setRedirectUrl(String redirectUrl){
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void afterPropertiesSet() {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;

    }
}
