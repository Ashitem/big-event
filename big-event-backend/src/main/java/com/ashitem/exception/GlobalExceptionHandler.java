package com.ashitem.exception;

import com.ashitem.pojo.Result;
import jakarta.validation.ConstraintViolationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        if (e instanceof ConstraintViolationException){
            return Result.error("参数填写不正确");
        }
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败！");
    }
}
