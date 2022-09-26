package com.venson.servicebase.exceptionhandler;

import com.venson.commonutils.Result;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        if(e instanceof AccessDeniedException){
            return Result.error().message("Not Enough Authentication");
        }else{
            return Result.error().message("Server Error");
        }
    }

    @ExceptionHandler(CustomizedException.class)
    @ResponseBody
    public Result CustomizedExceptionError(CustomizedException e){
        e.printStackTrace();
        return Result.error().message(e.getMessage()).data(e.getCode().toString(), e.getMsg());
    }
}
