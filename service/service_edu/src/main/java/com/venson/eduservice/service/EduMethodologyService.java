package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduMethodology;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-04
 */
public interface EduMethodologyService extends IService<EduMethodology> {

    List<EduMethodology> getMethodologyReviewList();
}
