package com.venson.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.venson.commonutils.PageUtil;
import com.venson.eduservice.entity.EduActivityPublished;
import com.venson.eduservice.mapper.EduActivityPublishedMapper;
import com.venson.eduservice.service.EduActivityPublishedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author venson
 * @since 2022-08-09
 */
@Service
public class EduActivityPublishedServiceImp extends ServiceImpl<EduActivityPublishedMapper, EduActivityPublished> implements EduActivityPublishedService {

    @Override
    public Map<String, Object> getPageActivityList(Integer page, Integer limit) {
        Page<EduActivityPublished> pageActivity = new Page<>(page, limit);
        LambdaQueryWrapper<EduActivityPublished> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(EduActivityPublished::getId);
        wrapper.eq(EduActivityPublished::getIsPublished,true);
        baseMapper.selectPage(pageActivity, wrapper);
        return PageUtil.toMap(pageActivity);
    }
}
