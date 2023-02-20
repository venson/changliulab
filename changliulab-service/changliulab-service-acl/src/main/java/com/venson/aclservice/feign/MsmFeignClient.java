package com.venson.aclservice.feign;

import com.venson.commonutils.Result;
import com.venson.servicebase.entity.ResetPasswordVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(value = "service-msm")
public interface MsmFeignClient {
    @PostMapping("/edumsm/admin/msm/resetEmail")
    Result<String> resetEmail(@RequestBody ResetPasswordVo passwordVo);
}
