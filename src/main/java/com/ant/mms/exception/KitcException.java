package com.ant.mms.exception;

import com.ant.mms.enums.ResultEnum;
import lombok.Getter;

/**
 * Created by wolf   2018/10/22
 */
@Getter
public class KitcException extends RuntimeException{

    private Integer code;

    public KitcException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public KitcException(Integer code, String message) {
        super(message);
        this.code = code;
    }


}
