package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduChapterSectionContent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-06-14
 */
public interface EduChapterSectionContentService extends IService<EduChapterSectionContent> {

    EduChapterSectionContent getChapterById(String id);

    EduChapterSectionContent getSectionById(String id);
}
