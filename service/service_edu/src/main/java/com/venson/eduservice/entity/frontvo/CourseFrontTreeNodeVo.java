package com.venson.eduservice.entity.frontvo;

import lombok.Data;

import java.util.List;

@Data
public class CourseFrontTreeNodeVo {
    private String title;
    private Long id;
    private List<CourseFrontTreeNodeVo> children;

    public CourseFrontTreeNodeVo(Long id, String title){
        this.id = id;
        this.title = title;
    }

}
