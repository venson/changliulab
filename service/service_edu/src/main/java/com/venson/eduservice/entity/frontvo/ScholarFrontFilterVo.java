package com.venson.eduservice.entity.frontvo;


import lombok.Data;

@Data
public class ScholarFrontFilterVo {
    private String year;
    private String authors;
    private String title;
    private Integer sortByDate;
    private Integer sortByCitations;
}
