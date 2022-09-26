package com.venson.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.Result;
import com.venson.eduservice.entity.EduScholar;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.frontvo.ScholarFrontFilterVo;
import com.venson.eduservice.entity.vo.ScholarFilterVo;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-06-18
 */
public interface EduScholarService extends IService<EduScholar> {

    Map<String, Object> getPageScholarList(Page<EduScholar> page, ScholarFrontFilterVo filterVo);


    Result getPageScholar(Integer page, Integer limit, ScholarFilterVo filterVo);
}
