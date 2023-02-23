package com.venson.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.commonutils.PageResponse;
import com.venson.commonutils.PageUtil;
import com.venson.educms.entity.BannerVo;
import com.venson.educms.entity.CrmBanner;
import com.venson.educms.entity.dto.BannerDTO;
import com.venson.educms.mapper.CrmBannerMapper;
import com.venson.educms.service.CrmBannerService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

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
    @Cacheable("banner")
    public List<CrmBanner> getActiveBannerFront() {
        LambdaQueryWrapper<CrmBanner> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmBanner::getEnable,true)
                .select(CrmBanner::getId,CrmBanner::getTitle,CrmBanner::getImageUrl,CrmBanner::getImageOutlinkUrl);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public PageResponse<CrmBanner> getPageBanner(Integer page, Integer limit) {
        Page<CrmBanner> bannerPage = new Page<>(page, limit);
        LambdaQueryWrapper<CrmBanner> wrapper = Wrappers.lambdaQuery(CrmBanner.class).select(CrmBanner::getId, CrmBanner::getTitle, CrmBanner::getImageUrl
                , CrmBanner::getImageOutlinkUrl, CrmBanner::getEnable);
        baseMapper.selectPage(bannerPage,wrapper);
        return PageUtil.toBean(bannerPage);
    }

    @Override
    public void switchEnableBanner(Long id) {
        CrmBanner banner = baseMapper.selectById(id);
        Assert.notNull(banner,"Banner not exits" );
        banner.setEnable(!banner.getEnable());
        baseMapper.updateById(banner);

    }

    @Override
    public Long addBanner(BannerDTO banner) {
        Assert.isTrue(isUsableTitle(null, banner.getTitle()), "Duplicated Title" );
        CrmBanner crmBanner = new CrmBanner();
        BeanUtils.copyProperties(banner,crmBanner);
        baseMapper.insert(crmBanner);
        return crmBanner.getId();
    }

    @Override
    @CacheEvict(value = "banner",allEntries = true)
    public void updateBanner(Long id,  BannerVo banner) {
        Assert.isTrue(id.equals(banner.getId()), "Not valid modification");
        checkTitleUsable(id, banner.getTitle());
        CrmBanner crmBanner = new CrmBanner();
        BeanUtils.copyProperties(banner,crmBanner);
        baseMapper.update(crmBanner,null);
    }

    private boolean isUsableTitle(Long id, String title){
        LambdaQueryWrapper<CrmBanner> wrapper = Wrappers.lambdaQuery(CrmBanner.class).eq(CrmBanner::getTitle, title);
        if(id!=null){
            wrapper.ne(CrmBanner::getId, id);
        }
        return baseMapper.selectOne(wrapper) == null;
    }
    private void checkTitleUsable(Long id, String title){
        LambdaQueryWrapper<CrmBanner> wrapper = Wrappers.lambdaQuery(CrmBanner.class).eq(CrmBanner::getTitle, title);
            wrapper.ne(id!=null,CrmBanner::getId, id);
        Assert.isNull(baseMapper.selectOne(wrapper) , "Duplicated Title");
    }
}
