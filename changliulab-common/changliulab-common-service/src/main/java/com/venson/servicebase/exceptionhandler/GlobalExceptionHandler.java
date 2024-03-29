package com.venson.servicebase.exceptionhandler;

import com.venson.commonutils.Result;
import com.venson.servicebase.exception.CustomizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> AccessDeniedError(Exception e){
            return Result.error(e.getMessage());
    }

    @ExceptionHandler(CustomizedException.class)
    public Result<String> CustomizedExceptionError(CustomizedException e){
        return Result.error(e.getMessage());
    }
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    public Result<String> IllegalExceptionError(Exception e){
        return Result.error(e.getMessage());
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<String> methodArgumentError(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder message = new StringBuilder();

        for (FieldError error :
                fieldErrors) {
            message.append(error.getDefaultMessage()).append(". ");
        }
        return Result.error(message.toString());
    }
    @ExceptionHandler(Exception.class)
    public Result<String> error(Exception e){
        log.error("Exception",e);
        return Result.error("Server Error");
    }
}
