package com.venson.security.adapter;

import java.util.List;

public interface AuthPathAdapter {
     List<String> pathWhiteList();
     List<String> loginPathList();
}
