package com.venson.security.utils;

import com.venson.security.entity.bo.UserContextInfoBO;
import org.springframework.security.core.context.SecurityContextHolder;

public class ContextUtils {
    private ContextUtils(){}
    public static UserContextInfoBO getUserContext(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserContextInfoBO){
            return (UserContextInfoBO)principal;
        }
        return null;

    }
}
