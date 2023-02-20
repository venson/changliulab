package com.venson.eduservice.entity.enums;

public enum PageType {
    NORMAL(1),
    REVIEW(2),
    ;
    final int value;


    PageType(int i) {
        value= i;
    }
}
