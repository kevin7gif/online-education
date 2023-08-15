package com.kevin.base.exception;

import com.kevin.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 * @author kevin
 * @version 1.0
 * @date 2023-07-23 9:05
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定出现什么异常时执行这个方法
    @ExceptionHandler(Exception.class)
    public R error(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().message("执行了全局异常处理...");
    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    public R error(ArithmeticException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理...");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    public R error(GuliException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
