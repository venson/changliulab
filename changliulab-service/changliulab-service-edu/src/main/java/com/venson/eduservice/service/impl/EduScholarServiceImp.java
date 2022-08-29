package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.commonutils.RMessage;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.entity.frontvo.ScholarFrontFilterVo;
import com.venson.eduservice.entity.vo.ScholarFilterVo;
import com.venson.eduservice.mapper.EduScholarMapper;
import com.venson.eduservice.service.EduScholarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-06-18
 */
@Service
public class EduScholarServiceImp extends ServiceImpl<EduScholarMapper, EduScholar> implements EduScholarService {


    @Override
    public Map<String, Object> getPageScholarList(Page<EduScholar> page, ScholarFrontFilterVo filterVo) {
        QueryWrapper<EduScholar> wrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(filterVo)){
            String authors = filterVo.getAuthors().toLowerCase(Locale.ROOT).trim();
            String[] authorSplit = authors.split("\\s*,\\s* ");
            String title = filterVo.getTitle().toLowerCase(Locale.ROOT).trim();
            String year = filterVo.getYear();
            Integer sortByCitations = filterVo.getSortByCitations();
            Integer sortByDate = filterVo.getSortByDate();
            if(!ObjectUtils.isEmpty(authors)){
                wrapper.like("authors",authors);
                if(!ObjectUtils.isEmpty(authorSplit[1]))
                    wrapper.or().like("authors",authorSplit[0]);
                if(!ObjectUtils.isEmpty(authorSplit[2]))
                    wrapper.or().like("authors",authorSplit[1]);
                if(!ObjectUtils.isEmpty(authorSplit[3]))
                    wrapper.or().like("authors",authorSplit[2]);
            }
            if(!ObjectUtils.isEmpty(title)){
                wrapper.like("titel", title);
            }
            if(!ObjectUtils.isEmpty(year)){
                wrapper.like("year",year);
            }
            wrapper.orderBy(sortByCitations !=0,sortByCitations==2, "citations");
            wrapper.orderBy(sortByDate !=0,sortByDate==2, "year");
        }
        Page<EduScholar> resPage = baseMapper.selectPage(page, wrapper);

        return PageUtil.toMap(resPage);
    }

    @Override
    public Map<String, Object> getPageScholarByMemberId(Long memberId, Integer pageNum, Integer limit) {
        return null;
    }

    @Override
    public RMessage getPageScholar(Integer page, Integer limit, ScholarFilterVo filterVo) {
        Page<EduScholar> pageScholar = new Page<>(page,limit);
        LambdaQueryWrapper<EduScholar> wrapper = new LambdaQueryWrapper<>();
        if(!ObjectUtils.isEmpty(filterVo)){
            wrapper.eq(StringUtils.hasText(filterVo.getYear()),EduScholar::getYear, filterVo.getYear());
            wrapper.like(StringUtils.hasText(filterVo.getAuthors()),EduScholar::getAuthors, filterVo.getAuthors());
            wrapper.like(StringUtils.hasText(filterVo.getTitle()),EduScholar::getTitle, filterVo.getTitle());
        }
        baseMapper.selectPage(pageScholar,wrapper);

        Map<String, Object> map = PageUtil.toMap(pageScholar);
        return RMessage.ok().data(map);
    }
}
