package com.ant.mms.service;

import com.ant.mms.vo.PyramidUserListVo;

import java.util.List;

/**
 * 说明：三级成员管理
 * Created by wolf   2018/12/23
 */

public interface PyramidService {

    List<PyramidUserListVo> list(String UserId);

}
