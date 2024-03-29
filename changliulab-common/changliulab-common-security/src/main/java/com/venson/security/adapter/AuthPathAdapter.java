package com.venson.security.adapter;

import org.springframework.web.util.pattern.PathPattern;

import java.util.List;

public interface AuthPathAdapter {
     List<String> pathWhiteList();
     List<PathPattern> patternWhiteList();

     List<PathPattern> patternDocList();
}
