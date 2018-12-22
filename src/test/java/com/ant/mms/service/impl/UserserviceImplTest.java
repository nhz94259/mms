package com.ant.mms.service.impl;

import com.ant.mms.common.Const;
import com.ant.mms.pojo.User;
import com.ant.mms.service.IUserService;
import com.ant.mms.utils.MD5Util;
import com.ant.mms.utils.R;
import com.ant.mms.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.security.KeyException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by wolf   2018/12/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserserviceImplTest {

    @Autowired private IUserService userservice;
    @Test
    public void login()throws KeyException{
         UserVo result = userservice.login("admin", "admin");
        if(result!=null){
            log.info("登录成功:{}|{}" );
        }else{
            log.info("登录失败：{}"  );
        }
    }

    @Test
    public void register() {
        User user = new User();
        user.setRole(Const.Role.ROLE_ADMIN);
        user.setId(UUID.randomUUID().toString());
        user.setUsername("admin");
        user.setPassword( "admin");
        user.setQuestion("1+1");
        user.setAnswer("2");
        user.setEmail("nhz94259@163.com");
        user.setInviteCode("AAAAAA");
          R result=userservice.register(user) ;
          if(result.isOk()){
              log.info("注册成功:{}",result.getStatus().getMsg() );
          }else{
              log.info("注册失败：{}",result.getStatus().getMsg());
          }
    }

    @Test
    public void checkValid() {
        R result1 = userservice.checkValid("nhz94259@163.com", Const.EMAIL);
        log.info(result1.getStatus().getMsg());
        R result2 = userservice.checkValid("admin", Const.USERNAME);
        log.info(result2.getStatus().getMsg());
    }

    @Test
    public void updateInformation() {
        User user = userservice.getById("34ec365d-8b16-485b-bd6d-d7e943ae21f6");
        user.setPassword(MD5Util.MD5EncodeUtf8("admin"));
        User user2 = userservice.updateInformation(user);
    }

    @Test
    public void getInformation() {
        User user = userservice.getById("77694c92-9708-4b8a-8421-e054e3d1dbec");
        log.info("info:{}",user);
        Assert.assertNotNull(user);
    }

    @Test
    public void selectQuestion() {
    }

    @Test
    public void deleteById() {
       Assert.assertEquals(userservice.deleteById("1").getStatus().getCode().longValue(),0);
    }
}