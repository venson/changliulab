package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduMethodology;
import com.venson.eduservice.entity.dto.MethodologyDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
public interface EduMethodologyService extends IService<EduMethodology> {

//    List<EduMethodology> getMethodologyReviewList();

    PageResponse<EduMethodology> getMethodologyPage(Integer page, Integer limit);

    MethodologyDTO getMethodologyViewById(Long id);

    void addMethodology(MethodologyDTO methodology);

    void updateMethodology(Long id, MethodologyDTO methodology);

    PageResponse<EduMethodology> getMethodologyReviewPage(Integer current, Integer size);
}
