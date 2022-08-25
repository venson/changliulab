package com.venson.eduservice.entity.subject;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectTreeNode implements Serializable {
    private Long id;
    private String title;
    private List<SubjectTreeNode> children= new ArrayList<>();

    public SubjectTreeNode() {
    }

    public SubjectTreeNode(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
