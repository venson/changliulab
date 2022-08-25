package com.venson.educms.controller;

import com.venson.commonutils.RMessage;
import com.venson.educms.entity.CrmBanner;
import com.venson.educms.service.CrmBannerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author venson
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/educms/admin/banner")
public class BannerAdminController {

    private final CrmBannerService service;

    public BannerAdminController(CrmBannerService service) {
        this.service= service;
    }

    @GetMapping("{page}/{limit}")
    @PreAuthorize("hasAuthority('banner.list')")
    public RMessage pageBanner(@PathVariable Integer page, @PathVariable Integer limit){
        Map<String, Object> map= service.getPageBanner(page,limit);
        return RMessage.ok().data(map);

    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('banner.list')")
    public RMessage getBanner(@PathVariable Long id){
        CrmBanner crmBanner = service.getById(id);
        return RMessage.ok().data( crmBanner);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('banner.add')")
    public RMessage addBanner(@RequestBody CrmBanner crmBanner){
        service.save(crmBanner);
        return RMessage.ok();
    }
    @PutMapping("")
    @PreAuthorize("hasAuthority('banner.edit')")
    public RMessage updateBanner(@RequestBody CrmBanner crmBanner){
        service.updateById(crmBanner);
        return RMessage.ok();
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('banner.remove')")
    public RMessage deleteBanner(@PathVariable Long id){
        service.removeById(id);
        return RMessage.ok();
    }
    @DeleteMapping("batch")
    @PreAuthorize("hasAuthority('banner.remove')")
    public RMessage deleteBannerBatch(@RequestBody List<String> list){
        service.removeBatchByIds(list);
        return RMessage.ok();
    }
}
