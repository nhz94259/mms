package com.ant.mms.controller;

import com.ant.mms.common.Const;
import com.ant.mms.enums.ResultEnum;
import com.ant.mms.exception.KitcException;
import com.ant.mms.pojo.User;
import com.ant.mms.service.IUserService;
import com.ant.mms.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by wolf
 */

@RestController
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired private IUserService iUserService;
    //管理员登录
    @PostMapping(value = "/login.do")
    public R login(@RequestParam("username") String username,
                   @RequestParam("password") String password,
                   HttpSession session)throws KitcException
    {
        R<User> response = iUserService.login(username,password);
        if(response.isOk()){
            User user = (User) response.getData(Const.CURRENT_USER);
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                //说明登录的是管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else{
                return R.errorMessage(ResultEnum.USER_INSUFFICIENT_PRIVILEGE.getMessage());
            }
        }
        return response;
    }
    //管理员允许删除用户
    @DeleteMapping(value = "/delete")
    public R  deleteUser(@RequestParam("id") String id,
                              HttpSession session )throws KitcException
    {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return R.errorResultEnum(ResultEnum.USER_INSUFFICIENT_PRIVILEGE);
        }
        if(user.getRole().equals(Const.Role.ROLE_ADMIN)){
            R <User> response= iUserService.deleteById(id);
            return response;
        }else{
            return R.errorMessage(ResultEnum.USER_INSUFFICIENT_PRIVILEGE.getMessage());
        }
    }
}
