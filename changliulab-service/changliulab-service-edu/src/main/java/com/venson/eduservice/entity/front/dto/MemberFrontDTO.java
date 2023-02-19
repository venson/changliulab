package com.venson.eduservice.entity.front.dto;

import com.venson.eduservice.entity.EduCoursePublished;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.entity.enums.MemberLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberFrontDTO implements Serializable {
    @Serial
    private static final long serialVersionUID= 2899238423482437L;

    private Long id;

    private String name;

    private String intro;

    private String career;

    private MemberLevel level;

    private String avatar;

    private String title;

    private List<EduCoursePublished> courses;
    private List<EduScholar> scholars;
}
