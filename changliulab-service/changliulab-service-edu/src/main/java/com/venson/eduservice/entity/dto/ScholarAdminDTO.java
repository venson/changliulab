package com.venson.eduservice.entity.dto;

import com.venson.eduservice.entity.EduMember;
import com.venson.eduservice.entity.EduScholarCitation;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class ScholarAdminDTO implements Serializable {

    @Serial
    private static final long serialVersionUID=2839343823L;

    private Long id;

    private String title;

    private String titleLinkUrl;

    private String googleLink;

    private Integer year;

    private String authors;

    private String publicationDate;

    private String journal;

    private Integer volume;

    private Integer issue;

    private String pages;

    private String publisher;

    private String description;

    private String linkText;

    private String linkUrl;

    private Integer totalCitations;

    private List<Long> memberIdList;

    private List<EduMember> memberList;

    private List<EduScholarCitation> citationList;
}
