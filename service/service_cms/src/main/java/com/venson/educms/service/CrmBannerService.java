package com.venson.educms.service;

import com.venson.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author venson
 * @since 2022-05-19
 */
public interface CrmBannerService extends IService<CrmBanner> {


    List<CrmBanner> getActiveBanner();

    Map<String, Object> getPageBanner(Integer page, Integer limit);
}
