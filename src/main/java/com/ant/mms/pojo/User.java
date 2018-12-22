package com.ant.mms.pojo;

import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by wolf
 */
@Entity
@Table
@Data
@DynamicUpdate
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private String Id;
    /** 电话. */
    private String cellPhone;
    /** 密码. */
    private String password;

    /** 用户名. */
    private String username;
    /** 邮箱. */
    private String email;
    /** 问题. */
    private String question;
    /** 答案. */
    private String answer;
    /** 角色. */
    private Integer role;
    /** 邀请码. */
    private String inviteCode;
    /** 上级id. */
    private String leaderId;

//MYSQL数据库完成
//    /** 创建时间. */
//    private Date createTime;
//    /** 更新时间. */
//    private Date updateTime;


}
