package com.ant.mms.enums;


import lombok.Getter;

/**
 * Created by wolf   2018/10/22
 */
@Getter
public enum ResultEnum {

    ERROR(-1,"fail"),
    SUCCESS(0,"ok"),
    CHECK_OK(1,"检验通过"),
    CHECK_FAIL(2,"检验失败"),
    UPDATE_FAIL(9,"更新失败"),
    PARAMETER_ERROR(11,"参数错误"),
    UNKNOW_ERROR(500,"未知错误，请联系管理员"),


 //300之后的开始
    USER_NEED_LOGIN(301,"用户未登录"),
    USER_INSUFFICIENT_PRIVILEGE(302,"权限受限，请联系管理员"),
    USER_IS_NOT_EXIST(303,"用户不存在"),
    USER_REGISTER_OK(304,"注册用户成功"),
    USER_REGISTER_FAIL(305,"注册用户失败"),
    USER_USERNAME_IS_EXIST(306,"用户名已存在"),
    USER_MAIL_IS_EXIST(307,"邮箱已注册"),
    User_PASSWORD_WRONG(308,"密码错误");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    ResultEnum( ) {

    }
}
