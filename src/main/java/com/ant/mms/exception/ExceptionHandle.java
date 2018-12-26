package com.ant.mms.exception;


import com.ant.mms.utils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wolf
 */
@ControllerAdvice
public class ExceptionHandle {
    //通用异常处理
//    @ExceptionHandler(value = KitcException.class)
//    @ResponseBody
//    public R handlerSellerException(KitcException e) {
//
//        return R.errorCodeMessage(e.getCode(),e.getMessage());
//    }
}
