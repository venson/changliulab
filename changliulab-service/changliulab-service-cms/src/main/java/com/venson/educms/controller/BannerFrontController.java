package com.venson.educms.controller;

import com.venson.commonutils.Result;
import com.venson.educms.entity.CrmBanner;
import com.venson.educms.service.CrmBannerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educms/front/banner")
public class BannerFrontController {
    private final CrmBannerService crmBannerService;

    public BannerFrontController(CrmBannerService crmBannerService) {
        this.crmBannerService = crmBannerService;
    }

    @GetMapping()
    public Result getBanner(){
        List<CrmBanner> list = crmBannerService.getActiveBanner();
        return Result.success().data("item",list);

    }
}
