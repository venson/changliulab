package com.venson.eduservice.service;

import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduScholar;
import com.baomidou.mybatisplus.extension.service.IService;
import com.venson.eduservice.entity.dto.ScholarAdminDTO;
import com.venson.eduservice.entity.vo.ScholarFilterVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-06-18
 */
public interface EduScholarService extends IService<EduScholar> {
    PageResponse<EduScholar> getPageScholar(Integer page, Integer limit, ScholarFilterVo filterVo);

    ScholarAdminDTO getScholarById(Long id);

    void updateScholar(ScholarAdminDTO scholar);
}
