package com.ant.mms.service;


import com.ant.mms.pojo.User;
import com.ant.mms.utils.R;
import com.ant.mms.vo.UserVo;

import java.util.List;

/**
 * Created by wolf
 */

public interface IUserService {

    UserVo login(String username, String password);

    R register(User user);

    R checkValid(String str, String type);

    User updateInformation(User user);

    User getById(String userId);

    R selectQuestion(String username);

    R deleteById(String id);
    /*
    密码重置使用


    List<String> checkAnswer(String username,String question,String answer);

    List<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    List<String> resetPassword(String passwordOld,String passwordNew,User user);*/
}
