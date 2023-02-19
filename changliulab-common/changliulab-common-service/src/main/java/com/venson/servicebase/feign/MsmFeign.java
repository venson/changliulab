package com.venson.servicebase.feign;

import com.venson.commonutils.Result;
import com.venson.servicebase.entity.ResetPasswordVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "service-msm")
public interface MsmFeign {
    @PostMapping("/edumsm/admin/msm/resetEmail")
    Result<String> resetEmail(@RequestBody ResetPasswordVo passwordVo);
}
