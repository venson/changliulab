package com.venson.eduservice.entity.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum ReviewType implements IEnum<Integer> {
    COURSE(1,"course"),
    CHAPTER(2,"chapter"),
    SECTION(3,"section"),
    ACTIVITY(4,"activity"),
    RESEARCH(5,"research"),
    METHODOLOGY(6, "methodology")

    ;

    private final String desc;
    private final int value;

    ReviewType(int value, String desc) {
        this.value =value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
