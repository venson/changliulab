package com.venson.eduservice.entity.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MemberQuery {

    private String name;

    private Integer level;

    private String begin;

    private String end;
}
