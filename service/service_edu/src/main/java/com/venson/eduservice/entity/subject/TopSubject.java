package com.venson.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TopSubject {
    private String id;
    private String title;
    private List<LevelISubject> children= new ArrayList<>();
}
