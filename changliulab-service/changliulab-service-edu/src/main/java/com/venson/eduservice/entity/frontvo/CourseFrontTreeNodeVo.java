package com.venson.eduservice.entity.frontvo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CourseFrontTreeNodeVo {
    private String title;
    private Long id;
    private List<CourseFrontTreeNodeVo> children;

    public CourseFrontTreeNodeVo(Long id, String title){
        this.id = id;
        this.title = title;
    }

}
