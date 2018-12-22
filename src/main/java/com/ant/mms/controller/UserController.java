package com.ant.mms.controller;


import com.ant.mms.common.Const;
import com.ant.mms.enums.ResultEnum;
import com.ant.mms.exception.KitcException;
import com.ant.mms.formvalid.RegisterForm;
import com.ant.mms.formvalid.UserForm;
import com.ant.mms.pojo.User;
import com.ant.mms.service.IUserService;
import com.ant.mms.utils.R;
import com.ant.mms.vo.UserVo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by wolf
 */
@RestController
@RequestMapping(value="/user")
@Slf4j
public class UserController {


    @Autowired private IUserService iUserService;

    //普通用户登录
    @PostMapping(value = "/login")
    public R<User> login(@RequestParam("username") String username ,
                         @RequestParam("password") String password,
                         HttpSession session)throws KitcException{
        R<User> response =iUserService.login(username,password);
        if (response.isOk()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
    //用户登出
    @GetMapping(value="/logout")
    public R  logout(HttpSession session)throws KitcException{
        session.removeAttribute(Const.CURRENT_USER);
        return R.success();
    }



    @PostMapping(value="/register")
    public R  register(@Valid RegisterForm form,
                BindingResult bindingResult)throws KitcException{
        if (bindingResult.hasErrors()) {
            log.error("【用戶注冊】参数不正确, userForm={}", form);
            throw new KitcException(ResultEnum.PARAMETER_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User user = new User();
        BeanUtils.copyProperties(form, user);
        return iUserService.register(user);
    }

    //获取用户信息
    @PostMapping(value = "/get")
    public R<User> getUserInfo(HttpSession session)throws KitcException{
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user!=null){
            return R.success().put(Const.CURRENT_USER,user);
        }
        return R.errorMessage(ResultEnum.USER_NEED_LOGIN.getMessage());
    }
    //更新信息
    @PostMapping(value = "/update")
    public R<User> updateUserInfo( HttpSession session,
                                   @Valid UserForm form,
                                   BindingResult bindingResult  )throws KitcException  {
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser==null){
            R.errorMessage(ResultEnum.USER_NEED_LOGIN.getMessage());
        }
        //TODO

        User user =iUserService.getById(((User) session.getAttribute(Const.CURRENT_USER)).getId());

        BeanUtils.copyProperties(form,user);
        User result=iUserService.updateInformation(user);
        if(result!=null){
            UserVo uv= new UserVo();
            BeanUtils.copyProperties(user,uv);
            session.setAttribute(Const.CURRENT_USER,uv);
            return R.success();
        }
        return R.error();
    }



}
