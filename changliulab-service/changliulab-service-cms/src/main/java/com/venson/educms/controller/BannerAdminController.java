package com.venson.educms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.Result;
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
    public Result pageBanner(@PathVariable Integer page, @PathVariable Integer limit){
        Map<String,Object>  map= service.getPageBanner(page,limit);
        return Result.success().data(map);

    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('banner.list')")
    public Result getBanner(@PathVariable Long id){
        CrmBanner crmBanner = service.getById(id);
        return Result.success().data(crmBanner);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('banner.add')")
    public Result addBanner(@RequestBody CrmBanner crmBanner){
        service.save(crmBanner);
        return Result.success();
    }
    @PutMapping("")
    @PreAuthorize("hasAuthority('banner.edit')")
    public Result updateBanner(@RequestBody CrmBanner crmBanner){
        service.updateById(crmBanner);
        return Result.success();
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('banner.remove')")
    public Result deleteBanner(@PathVariable Long id){
        service.removeById(id);
        return Result.success();
    }
    @DeleteMapping("batch")
    @PreAuthorize("hasAuthority('banner.remove')")
    public Result deleteBannerBatch(@RequestBody List<String> list){
        service.removeBatchByIds(list);
        return Result.success();
    }
}
