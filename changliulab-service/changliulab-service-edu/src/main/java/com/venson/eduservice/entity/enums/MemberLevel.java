package com.venson.eduservice.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum MemberLevel implements IEnum<Integer> {
    INTERN(1,"Intern"),
    FORMER_MEMBER(2,"Former member"),
    CURRENT_MEMBER(3,"Member"),
    TECH(4,"Tech"),
    PI(5,"Principal investigator");

    private Integer value;
    private String desc;

    MemberLevel(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }
    @Override
    public Integer getValue() {
        return value;
    }
    public String getDesc(){
        return desc;
    }
}
