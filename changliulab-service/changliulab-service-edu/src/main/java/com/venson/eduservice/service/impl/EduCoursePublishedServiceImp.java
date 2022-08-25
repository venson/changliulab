package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduChapterPublished;
import com.venson.eduservice.entity.EduCoursePublished;
import com.venson.eduservice.entity.EduSectionPublished;
import com.venson.eduservice.entity.frontvo.CourseFrontFIlterVo;
import com.venson.eduservice.entity.frontvo.CourseFrontInfoVo;
import com.venson.eduservice.entity.frontvo.CourseFrontTreeNodeVo;
import com.venson.eduservice.mapper.EduCoursePublishedMapper;
import com.venson.eduservice.service.EduChapterPublishedService;
import com.venson.eduservice.service.EduCoursePublishedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.venson.eduservice.service.EduSectionPublishedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-07-12
 */
@Service
public class EduCoursePublishedServiceImp extends ServiceImpl<EduCoursePublishedMapper, EduCoursePublished> implements EduCoursePublishedService {
    @Autowired
    private EduChapterPublishedService chapterPublishedService;
    @Autowired
    private EduSectionPublishedService sectionPublishedService;


    @Override
    public Map<String, Object> getFrontPageCourseList(Integer page, Integer limit, CourseFrontFIlterVo courseFrontVo) {
        Page<EduCoursePublished> pageCourse = new Page<>(page,limit);
        LambdaQueryWrapper<EduCoursePublished> wrapper = new LambdaQueryWrapper<>();
        if(!ObjectUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq(EduCoursePublished::getSubjectParentId, courseFrontVo.getSubjectParentId());
        }
        if(!ObjectUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq(EduCoursePublished::getSubjectId, courseFrontVo.getSubjectId());
        }

        // order by views
        Integer viewSort;
        if(!ObjectUtils.isEmpty(courseFrontVo.getViewSort())){
            viewSort = courseFrontVo.getViewSort() ;
            setWrapperSort(wrapper,viewSort,EduCoursePublished::getViewCount);
        }

        // order by create date
        Integer createSort;
        if(!ObjectUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            createSort = courseFrontVo.getGmtCreateSort();
            setWrapperSort(wrapper,createSort,EduCoursePublished::getGmtCreate);
        }

        // update date sort
        Integer updateSort;
        if(!ObjectUtils.isEmpty(courseFrontVo.getUpdateSort())){
            updateSort= courseFrontVo.getUpdateSort();
            setWrapperSort(wrapper,updateSort,EduCoursePublished::getGmtModified);
        }
        baseMapper.selectPage(pageCourse,wrapper);

        return PageUtil.toMap(pageCourse);
    }

    @Override
    public CourseFrontInfoVo getFrontCourseInfo(Long id) {
        return baseMapper.getFrontCourseInfo(id);
    }

    @Override
    public List<CourseFrontTreeNodeVo> getCourseFrontTreeByCourseId(Long id) {
        LambdaQueryWrapper<EduChapterPublished> chapterWrapper = new LambdaQueryWrapper<>();
        chapterWrapper.eq(EduChapterPublished::getCourseId,id)
            .orderByAsc(Arrays.asList(EduChapterPublished::getSort, EduChapterPublished::getId));
        List<EduChapterPublished> chapterList = chapterPublishedService.list(chapterWrapper);
        LambdaQueryWrapper<EduSectionPublished> sectionWrapper = new LambdaQueryWrapper<>();
        sectionWrapper.eq(EduSectionPublished::getCourseId, id)
                .orderByAsc(Arrays.asList(EduSectionPublished::getSort,EduSectionPublished::getId));
        List<EduSectionPublished> sectionList = sectionPublishedService.list(sectionWrapper);

        List<CourseFrontTreeNodeVo> treeRootNode = chapterList.parallelStream().map(o ->
            new CourseFrontTreeNodeVo(o.getId(), o.getTitle())
        ).collect(Collectors.toList());

        Map<Long, List<CourseFrontTreeNodeVo>> treeLeafNode = sectionList.parallelStream()
                .collect(Collectors.groupingBy(EduSectionPublished::getChapterId,
                        Collectors.mapping(o -> new CourseFrontTreeNodeVo(o.getId(), o.getTitle()),
                                Collectors.toList())));

        treeRootNode.parallelStream().forEach(o->o.setChildren(treeLeafNode.get(o.getId())));
        return treeRootNode;
    }

    private void setWrapperSort(LambdaQueryWrapper<EduCoursePublished> wrapper, Integer sortValue, SFunction<EduCoursePublished,?> column){
        boolean sortFlag = sortValue !=null && sortValue!=0;
        boolean sortIsAsc = sortFlag && sortValue==2;
        wrapper.orderBy(sortFlag,sortIsAsc,column);
    }
}
