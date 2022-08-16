package com.venson.educms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.RMessage;
import com.venson.educms.entity.CrmBanner;
import com.venson.educms.service.CrmBannerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/educms/bannerAdmin")
//@CrossOrigin
public class BannerAdminController {

    private final CrmBannerService crmBannerService;

    public BannerAdminController(CrmBannerService crmBannerService) {
        this.crmBannerService = crmBannerService;
    }

    @GetMapping("banner/{page}/{limit}")
    public RMessage pageBanner(@PathVariable Integer page, @PathVariable Integer limit){


        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        crmBannerService.page(pageBanner,null);
        return RMessage.ok()
                .data("item",pageBanner.getRecords())
                .data("total", pageBanner.getTotal());

    }

    @GetMapping("banner/{id}")
    public RMessage getBanner(@PathVariable Long id){
        CrmBanner crmBanner = crmBannerService.getById(id);
        return RMessage.ok().data("item", crmBanner);
    }

    @PostMapping("banner")
    @CacheEvict(value = "banner", allEntries = true)
    public RMessage saveBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return RMessage.ok();
    }
    @PutMapping("banner")
    @CacheEvict(value = "banner", allEntries = true)
    public RMessage updateBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return RMessage.ok();
    }
    @PutMapping("bannerBatch")
    @CacheEvict(value = "bannerBatch", allEntries = true)
    public RMessage updateBatchBanner(@RequestBody List<CrmBanner> list){
        crmBannerService.updateBatchById(list);
        return RMessage.ok();
    }
    @DeleteMapping("banner/{id}")
    @CacheEvict(value = "banner", allEntries = true)
    public RMessage deleteBanner(@PathVariable Long id){
        crmBannerService.removeById(id);
        return RMessage.ok();
    }
    @DeleteMapping("banner/")
    @CacheEvict(value = "banner", allEntries = true)
    public RMessage deleteBannerBatch(@RequestBody List<String> list){
        crmBannerService.removeBatchByIds(list);
        return RMessage.ok();
    }
}
