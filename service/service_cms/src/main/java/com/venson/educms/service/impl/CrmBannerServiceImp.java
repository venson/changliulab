package com.venson.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.PageUtil;
import com.venson.educms.entity.CrmBanner;
import com.venson.educms.mapper.CrmBannerMapper;
import com.venson.educms.service.CrmBannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-05-19
 */
@Service
public class CrmBannerServiceImp extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(value = "banner", key = "'getActiveBanner'")
    public List<CrmBanner> getActiveBanner() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.eq("active",1);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public Map<String, Object> getPageBanner(Integer page, Integer limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        baseMapper.selectPage(bannerPage,null);
        return PageUtil.toMap(bannerPage);
    }
}
