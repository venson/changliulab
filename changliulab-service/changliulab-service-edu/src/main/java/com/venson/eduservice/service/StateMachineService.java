package com.venson.eduservice.service;

import com.venson.eduservice.entity.EduReview;
import com.venson.eduservice.entity.dto.ReviewApplyVo;
import com.venson.eduservice.entity.enums.ReviewType;


public interface StateMachineService {

    void requestNoneReview(ReviewApplyVo ctx);

    void doAlterReviewByCtx(ReviewApplyVo ctx);

    EduReview getUnfinishedReviewByRefIdType(Long refId, ReviewType refType);


    void requestRejectedReview(ReviewApplyVo ctx);
}
