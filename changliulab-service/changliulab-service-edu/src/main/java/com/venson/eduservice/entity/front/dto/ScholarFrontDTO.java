package com.venson.eduservice.entity.front.dto;

import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.EduScholar;
import com.venson.eduservice.entity.EduScholarCitation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScholarFrontDTO implements Serializable {
    @Serial
    private static final long serialVersionUID=2938423489L;
    private EduScholar scholar;
    private List<EduMember> members;
    private List<EduScholarCitation> citations;

}
