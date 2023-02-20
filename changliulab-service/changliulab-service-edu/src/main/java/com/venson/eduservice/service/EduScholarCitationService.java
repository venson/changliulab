package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduScholarCitation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-06-20
 */
public interface EduScholarCitationService extends IService<EduScholarCitation> {

    List<EduScholarCitation> getCitationsByScholarId(Long id);

    void updateScholarCitation(List<EduScholarCitation> citationList, Long scholar);
}
