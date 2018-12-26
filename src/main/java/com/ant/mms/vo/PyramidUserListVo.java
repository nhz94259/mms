package com.ant.mms.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 每代会员信息
 * Created by wolf   2018/12/23
 */
@Data
public class PyramidUserListVo implements Serializable {

    @JsonProperty("generation")
    private String generation;

    @JsonProperty("userlist")
    private List<UserVo> productInfoVOList;
}
