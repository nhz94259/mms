package com.ant.mms.controller;


import com.ant.mms.common.Const;
import com.ant.mms.enums.ResultEnum;
import com.ant.mms.exception.KitcException;
import com.ant.mms.formvalid.RegisterForm;
import com.ant.mms.formvalid.UserQuestAndAnswerForm;
import com.ant.mms.formvalid.UserUpdateForm;
import com.ant.mms.pojo.User;
import com.ant.mms.service.IUserService;
import com.ant.mms.service.PyramidService;
import com.ant.mms.utils.R;
import com.ant.mms.vo.PyramidUserListVo;
import com.ant.mms.vo.UserVo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by wolf
 */
@RestController
@RequestMapping(value="/user")
@Slf4j
public class UserController {


    @Autowired private IUserService iUserService;
    @Autowired private PyramidService ipyramidService;

    //普通用户登录
    @PostMapping(value = "/login")
    public R<User> login(@RequestParam("username") String username ,
                         @RequestParam("password") String password,
                         HttpSession session) throws KitcException{
        UserVo user =iUserService.login(username,password);
        if (user!=null){
            session.setAttribute(Const.CURRENT_USER,user);
        }
        return R.success().put("userinfo",user);
    }
    //用户登出
    @GetMapping(value="/logout")
    public R  logout(HttpSession session)throws KitcException{
       Object user =   session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return R.successMessage("用户已登出，请勿重复操作");
        }
        session.removeAttribute(Const.CURRENT_USER);
        return R.errorMessage("用户已登出");
    }



    @PostMapping(value="/register")
    public R  register(@Valid RegisterForm userform,
                BindingResult bindingResult)throws KitcException{
        if (bindingResult.hasErrors()) {
            log.error("【用戶注冊】参数不正确, userForm={}", userform);
            throw new KitcException(ResultEnum.PARAMETER_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }


        return iUserService.register(userform);
    }

    //获取用户信息
    @GetMapping(value = "/get")
    public R<User> getUserInfo(HttpSession session)throws KitcException{

        UserVo user =  (UserVo) session.getAttribute(Const.CURRENT_USER);

        if(user!=null){
            return R.success().put(Const.CURRENT_USER,user);
        }
        return R.errorMessage(ResultEnum.USER_NEED_LOGIN.getMessage());
    }


    //更新信息
    @PostMapping(value = "/update")
    public R<User> updateUserInfo( HttpSession session,
                                   @Valid UserUpdateForm form,
                                   BindingResult bindingResult  )throws KitcException  {

        UserVo currentUser =  (UserVo) session.getAttribute(Const.CURRENT_USER);

        if(currentUser==null){
            R.errorMessage(ResultEnum.USER_NEED_LOGIN.getMessage());
        }
        if (bindingResult.hasErrors()) {
            log.error("【用戶更新信息】参数不合规, userForm={}", form);
            throw new KitcException(ResultEnum.PARAMETER_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        User user =iUserService.getById(((User) session.getAttribute(Const.CURRENT_USER)).getId());

        BeanUtils.copyProperties(form,user);

        User result=iUserService.updateInformation(user);
        if(result!=null){
            BeanUtils.copyProperties(user,currentUser);
            session.setAttribute(Const.CURRENT_USER,currentUser);
            return R.success();
        }
        return R.error();
    }

    @GetMapping(value = "/updateqa")
    public R updateQuestionAndQuestion(HttpSession session,
                                       @Valid UserQuestAndAnswerForm qaform,
                                       BindingResult bindingResult ) throws KitcException{
        if(bindingResult.hasErrors()){
            log.error("【用户安全问题不合规】：{}",qaform);
            throw  new KitcException(ResultEnum.PARAMETER_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        UserVo currentUser = (UserVo)session.getAttribute(Const.CURRENT_USER);

        if(currentUser!=null)
            return R.errorMessage(ResultEnum.USER_NEED_LOGIN.getMessage());


        User  user = iUserService.getById(currentUser.getId());
        user.setQuestion(qaform.getQuestion());
        user.setAnswer(qaform.getAnswer());
        return iUserService.update(user);
    }


    //得到自己线下会员
    @GetMapping(value = "/getgroup")
    public R getPyramidList(@RequestParam (value ="userid") String userid) {
        List<PyramidUserListVo> grouplist = ipyramidService.list(userid);
        return R.success().put("list",grouplist);
    }

}
