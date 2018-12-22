package com.ant.mms.service.impl;


import com.ant.mms.common.Const;
import com.ant.mms.enums.ResultEnum;
import com.ant.mms.exception.KitcException;
import com.ant.mms.pojo.User;
import com.ant.mms.respository.UserRepository;
import com.ant.mms.service.IUserService;
import com.ant.mms.utils.MD5Util;
import com.ant.mms.utils.R;
import com.ant.mms.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.codec.digest.DigestUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public  UserVo  login(String username, String password) {
        int resultCount = repository.findUserByUsername( username ).size();
        if(resultCount == 0 ){
            throw new KitcException(ResultEnum.USER_IS_NOT_EXIST.getCode(),ResultEnum.USER_IS_NOT_EXIST.getMessage());
        }
        String md5Password = MD5Util.MD5EncodeUtf8( password) ;
        User user  =  repository.findUserByUsernameAndPassword(username,md5Password);
        if(user == null){
           throw new KitcException(ResultEnum.USER_PASSWORD_WRONG );
        }
        user.setPassword( StringUtils.EMPTY);
        UserVo userVo= new UserVo();
        BeanUtils.copyProperties(user,userVo);
        return  userVo ;
    }

    @Override
    @Transactional
    public R register(User user) {
        R validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if(!validResponse.isOk()){
            return validResponse;
        }
        //注册时减少输入故没有邮箱检验
      /*  validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isOk()){
            return validResponse;
        }*/
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()) );
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
    public R selectQuestion(String username) {
        R e=this.checkValid(username,Const.USERNAME);
        if(e.isOk()){
            User user = repository.findUserByUsername(username).get(0);
            return R.success().put("question",user.getQuestion());
        }else{
            return R.errorMessage(ResultEnum.USER_INSUFFICIENT_PRIVILEGE.getMessage());
        }
    }

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
