package com.ant.mms.exception;


import com.ant.mms.enums.ResultEnum;
import com.ant.mms.utils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * Created by wolf
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    //全局异常处理
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public R handlerException(Exception e) {
        if(e instanceof KitcException){
            return R.errorCodeMessage( ((KitcException)e).getCode(),e.getMessage());
        }
        return R.errorCodeMessage(ResultEnum.ERROR.getCode(),e.getMessage());
    }

}
