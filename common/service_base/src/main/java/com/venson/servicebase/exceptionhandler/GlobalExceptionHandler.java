package com.venson.servicebase.exceptionhandler;

import com.venson.commonutils.RMessage;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RMessage error(Exception e){
        e.printStackTrace();
        return RMessage.error().message("global error");
    }

    @ExceptionHandler(CustomizedException.class)
    @ResponseBody
    public RMessage CustomizedExceptionError(CustomizedException e){
        e.printStackTrace();
        return RMessage.error().message("Customized Exception error").data(e.getCode().toString(), e.getMsg());
    }
}
