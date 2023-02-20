package com.venson.eduservice.mapper;

import com.venson.eduservice.entity.EduActivityPublished;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.venson.eduservice.entity.front.dto.ActivityFrontBriefDTO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author venson
 * @since 2022-08-09
 */
public interface EduActivityPublishedMapper extends BaseMapper<EduActivityPublished> {

    List<ActivityFrontBriefDTO> getFrontIndexActivity();
}
