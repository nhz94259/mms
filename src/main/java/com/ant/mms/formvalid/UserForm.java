package com.ant.mms.formvalid;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by wolf   2018/12/21
 */
@Data
public class UserForm {

    private final static  String cellRegexp="^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
    private final static  String StringOrNumbeRegexp = "^[A-Za-z][A-Za-z1-9_-]+$";
//用户名建立后无法被修改
//    /** 用戶名. */
//    @NotEmpty(message = "用户名不能为空，请核对")
//    @Pattern(regexp = StringOrNumbeRegexp,message = "非法字符请检查")
//    private String username;
    /** 密码. */
    @Size(min=6,max = 16,message =  "密码长度为6至16位")
    @Pattern(regexp = StringOrNumbeRegexp,message = "非法字符请检查")
    private String password;
    /** 电话. */
    @Pattern( regexp=cellRegexp,message = "电话填写有误，请核对")
    private String cellphone;
    /** 邮箱. */
    @Email(message = "邮箱格式有误，请核对")
    private String email;
    /** 问题. */
    @NotEmpty(message = "问题不能为空")
    private String question;
    /** 答案. */
    @NotEmpty(message = "答案不能为空")
    private String answer;


}
