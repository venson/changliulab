package com.venson.aclservice.feign;

import com.venson.aclservice.entity.vo.ResetPasswordVo;
import com.venson.commonutils.RMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "service-msm")
public interface MsmFeignClient {
    @PostMapping("/edumsm/admin/msm/resetEmail")
    RMessage resetEmail(@RequestBody ResetPasswordVo passwordVo);
}
