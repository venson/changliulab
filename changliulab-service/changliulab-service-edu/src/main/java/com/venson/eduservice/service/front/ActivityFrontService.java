package com.venson.eduservice.service.front;

import com.venson.commonutils.PageResponse;
import com.venson.eduservice.entity.EduActivityPublished;
import com.venson.eduservice.entity.front.dto.ActivityFrontBriefDTO;
import com.venson.eduservice.entity.front.dto.ActivityFrontDTO;

import java.util.List;

public interface ActivityFrontService {
    PageResponse<EduActivityPublished> getPageActivity(Integer page, Integer limit);

    ActivityFrontDTO getActivityById(Long id);

    List<ActivityFrontBriefDTO> getFrontIndexActivity();
}
