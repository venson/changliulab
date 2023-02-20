package com.venson.educms.service;

import com.venson.commonutils.PageResponse;
import com.venson.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.educms.entity.dto.BannerDTO;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author venson
 * @since 2022-05-19
 */
public interface CrmBannerService extends IService<CrmBanner> {


    List<CrmBanner> getActiveBannerFront();

    PageResponse<CrmBanner> getPageBanner(Integer page, Integer limit);

    void switchEnableBanner(Long id);

    Long addBanner(BannerDTO banner);

    void updateBanner(Long id, CrmBanner crmBanner);
}
