package com.ant.mms.service.impl;


import com.ant.mms.common.Const;
import com.ant.mms.enums.ResultEnum;
import com.ant.mms.exception.KitcException;
import com.ant.mms.pojo.User;
import com.ant.mms.respository.UserRepository;
import com.ant.mms.service.IUserService;
import com.ant.mms.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by wolf
 */
@Service
@Slf4j
public class UserserviceImpl implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public R<User> login(String username, String password) {
        int resultCount = repository.findUserByUsername( username ).size();
        if(resultCount == 0 ){
            return R.errorMessage(ResultEnum.USER_IS_NOT_EXIST.getMessage());
        }
        String md5Password = DigestUtils.md5( password).toString();
        User user  =  repository.findUserByUsernameAndPassword(username,md5Password);
        if(user == null){
            return R.errorMessage(ResultEnum.User_PASSWORD_WRONG.getMessage());
        }
        user.setPassword( StringUtils.EMPTY);
        return R.success().put(Const.USERINFO,user);
    }

    @Override
    @Transactional
    public R register(User user) {
        R validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if(!validResponse.getStatus().getCode().equals(ResultEnum.SUCCESS.getCode())){
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.getStatus().getCode().equals(ResultEnum.SUCCESS.getCode())){
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(DigestUtils.md5(user.getPassword()).toString());
        user.setId(UUID.randomUUID().toString());
        User result =repository.save(user);
        log.info("用户注册：info={}",user.toString());
        if(result == null){
            return R.errorMessage( ResultEnum.USER_REGISTER_FAIL.getMessage());
        }
        return R.successMessage(ResultEnum.USER_REGISTER_OK.getMessage());
    }

    @Override
    public R checkValid(String str, String type) {
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = repository.findUserByUsername(str).size();
                if(resultCount > 0 ){
                    return R.errorMessage(ResultEnum.USER_USERNAME_IS_EXIST.getMessage());
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = repository.findUserByEmail(str).size();
                if(resultCount > 0 ){
                    return R.errorMessage(ResultEnum.USER_MAIL_IS_EXIST.getMessage());
                }
            }
        }else{
            return R.errorMessage(ResultEnum.PARAMETER_ERROR.getMessage());
        }
        return R.successMessage(ResultEnum.CHECK_OK.getMessage() );
    }

    @Override
    public User updateInformation(User user) {
        User result= repository.save(user);
        //User u2= repository.findUserByUsername(user.getUsername()).get(0);
        if(result ==null){
            throw new KitcException(ResultEnum.UPDATE_FAIL);
        }
            return result;
    }

    @Override
    public User getById(String userId) {
        return repository.findById(userId).get();
    }

    @Override
    public R selectQuestion(String username) { return  this.checkValid(username,Const.USERNAME); }

    @Override
    public R deleteById(String id) {
        User user = repository.findById(id).get();
        if(user!=null){
            repository.delete(user);
        return R.success();
        }else{
            return R.errorMessage(ResultEnum.USER_IS_NOT_EXIST.getMessage()+"删除失败！");
        }
    }
}
