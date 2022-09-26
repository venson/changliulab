package com.venson.eduservice.service.front;

import java.util.Map;

public interface ScholarFrontService {
    Map<String, Object> getPageScholarByMemberId(Long id, Integer page, Integer limit);
}
