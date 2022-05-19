package com.venson.eduservice.client;

import com.venson.commonutils.RMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    @DeleteMapping("/eduvod/edu-video/removeAliVideo/{id}")
    RMessage removeAliVideo(@PathVariable("id") String id);
}
