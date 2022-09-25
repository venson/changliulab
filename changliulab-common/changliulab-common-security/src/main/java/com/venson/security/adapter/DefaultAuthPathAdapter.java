package com.venson.security.adapter;

import com.venson.security.entity.constant.AuthConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DefaultAuthPathAdapter implements AuthPathAdapter{
    private final List<String> whiteList = new ArrayList<>();
    private final List<String> loginPathList = new ArrayList<>();
    {
        log.info("init login path");
        loginPathList.add(AuthConstants.LOGIN_PATH_ADMIN);
        loginPathList.add(AuthConstants.LOGIN_PATH_USER);
        whiteList.add(AuthConstants.FRONT_PATTERN);
    }


    @Override
    public List<String> pathWhiteList() {
        return whiteList;
    }

    @Override
    public List<String> loginPathList() {
        return loginPathList;
    }
}
