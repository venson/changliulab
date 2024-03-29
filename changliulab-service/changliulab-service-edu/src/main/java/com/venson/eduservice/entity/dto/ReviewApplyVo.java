package com.venson.eduservice.entity.dto;

import com.venson.eduservice.entity.EduReviewMsg;
import com.venson.eduservice.entity.enums.ReviewAction;
import com.venson.eduservice.entity.enums.ReviewStatus;
import com.venson.eduservice.entity.enums.ReviewType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReviewApplyVo {
    @NotNull
    private Long id;
    @NotNull
    private ReviewType type;
    @NotNull
    private Long userId;
    @NotBlank
    private String userName;
    private String msg;
    private Long parentId;
    private ReviewType parentType;

    private ReviewStatus from;
    private ReviewStatus to;


    private ReviewAction action;

    public void copyRequestMsgToEduReviewMsg(EduReviewMsg msg){
        msg.setRequestUserId(this.userId);
        msg.setRequestUsername(this.userName);
        msg.setRequestMsg(this.msg);
        msg.setRefId(this.id);
        msg.setRefType(this.type);
    }
    public void copyReviewMsgToEduReviewMsg(EduReviewMsg msg){
        msg.setReviewUserId(this.userId);
        msg.setReviewUsername(this.userName);
        msg.setReviewMsg(this.msg);
        msg.setReviewAction(this.action);
    }
}
