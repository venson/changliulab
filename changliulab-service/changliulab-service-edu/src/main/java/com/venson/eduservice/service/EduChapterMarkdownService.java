package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduChapterMarkdown;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
public interface EduChapterMarkdownService extends IService<EduChapterMarkdown> {

    void saveOrUpdateToPublishedMd(List<Long> updateIdList);
}
