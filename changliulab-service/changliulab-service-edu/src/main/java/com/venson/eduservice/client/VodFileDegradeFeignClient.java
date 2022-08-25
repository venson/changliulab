package com.venson.eduservice.client;

import com.venson.commonutils.RMessage;
import com.venson.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public RMessage removeAliVideo(Long id) {
        return RMessage.error().message("Video service is down");
    }
}
