package com.venson.user.controller;

import com.venson.user.utils.WxUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
//@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @GetMapping("login")
    public String wxLogin(){
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect"
                + "?appid=%s"
                + "&redirect_uri=%s"
                + "&response_type=code"
                + "&scope=snsapi_login"
                + "&state=%s"
                + "#weichat_redirect";


        String url = null;
        try {
            String redirectUrl = URLEncoder.encode(WxUtils.WX_OPEN_REDIRECT_URL, "utf-8");
            url = String.format(baseUrl, WxUtils.WX_OPEN_APP_ID,
                    redirectUrl, "ChangLiu Lab");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "redirect:" + url;
    }

    @GetMapping("callback")
    public String callback(String code ,String state){
        System.out.println(code);
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token"
                + "?appid=%s"
                + "&secret=%s"
                + "&code=%s"
                + "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl, WxUtils.WX_OPEN_APP_ID,
                WxUtils.WX_OPEN_APP_SECRET, code);


        return "redirect:http://localhost:3000";
    }
}
