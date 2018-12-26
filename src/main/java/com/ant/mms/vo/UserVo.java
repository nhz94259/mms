package com.ant.mms.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by wolf   2018/12/21
 */
@Data
public class UserVo {

    private String id;

    private String username;

    private Integer role;

    private String inviteCode;

    private String totalAccount;//消费总金额

    private String totalRebate;//消费总返利
}
