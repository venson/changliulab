package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.venson.eduservice.entity.EduChapter;
import com.venson.eduservice.entity.EduVideo;
import com.venson.eduservice.entity.chapter.ChapterVo;
import com.venson.eduservice.entity.chapter.VideoVo;
import com.venson.eduservice.mapper.EduChapterMapper;
import com.venson.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.eduservice.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-11
 */
@Service
@Slf4j
public class EduChapterServiceImp extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    private final EduVideoService eduVideoService;

    public EduChapterServiceImp(EduVideoService eduVideoService) {
        this.eduVideoService = eduVideoService;
    }

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(wrapperChapter);


        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> videoList = eduVideoService.list(wrapperVideo);
        List<ChapterVo> finalList  = new ArrayList<>();

        Map<String, List<VideoVo>> videoVoMap = videoList.parallelStream().
                collect(Collectors.groupingBy(EduVideo::getChapterId,
                        Collectors.mapping(o -> new VideoVo(o.getId(), o.getTitle()),
                                Collectors.toList())));

        for (EduChapter chapter:
             chapterList) {
            ChapterVo chapterVoTemp = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVoTemp);
            List<VideoVo> videoVos = videoVoMap.get(chapterVoTemp.getId());
            chapterVoTemp.setChildren(videoVos);
            finalList.add(chapterVoTemp);
        }

        return finalList;
    }
}
