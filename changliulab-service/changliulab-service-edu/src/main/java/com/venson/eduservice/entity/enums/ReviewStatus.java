package com.venson.eduservice.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;


public enum ReviewStatus implements IEnum<Integer> {
    NONE(1,"none"),
    APPLIED(2,"applied"),
    REJECTED(3,"rejected"),
    FINISHED(4,"finished"),
    PARTIAL(5,"partial"),

    ;
    private Integer value;
    private String desc;

    ReviewStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;

    }

    @Override
    public Integer getValue() {
        return this.value;
    }
    public String getDesc(){ return this.desc;}
}
