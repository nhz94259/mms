package com.ant.mms.respository;

import com.ant.mms.common.Const;
import com.ant.mms.pojo.User;
import com.ant.mms.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by wolf   2018/12/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserRepositoryTest {
    @Autowired private UserRepository repository;

    @Test
    public void findUserByUsernameAndPassword() {
        Assert.assertNotNull(repository.findUserByUsernameAndPassword("admin","[B@786ff0ea"));
    }

    @Test
    public void findUserByUsername() {
        Assert.assertNotNull(repository.findUserByUsername("admin"));
    }

    @Test
    public void findUserByEmail() {
        Assert.assertNotNull(repository.findUserByEmail("nhz94259@163.com"));
    }

    @Test
    public void save1(){
        User user = new User();
        user.setRole(Const.Role.ROLE_ADMIN);
        user.setId("[B@786ff0ea");
        user.setAnswer("2");
        user.setEmail("nhz94259@163.com");
        user.setInviteCode("AAAAAA1");
        User savestatus=repository.save(user);
        Assert.assertNotNull(savestatus);
    }

    @Test
    public void save(){
        User user = new User();
        user.setRole(Const.Role.ROLE_ADMIN);
        user.setId(UUID.randomUUID().toString());
        user.setUsername("admin");
        user.setPassword(DigestUtils.md5("admin").toString());
        user.setQuestion("1+1");
        user.setAnswer("2");
        user.setEmail("nhz94259@163.com");
        user.setInviteCode("AAAAAA");
        User savestatus=repository.save(user);
        Assert.assertNotNull(savestatus);
    }

    @Test
    public void update(){
        User user=repository.findUserByUsername("admin").get(0);
        log.info("user:{}",user);
        Assert.assertNotNull(user);
        user.setLeaderId("A");
        User resultStatus = repository.save(user);
        Assert.assertNotNull(resultStatus);
    }
}