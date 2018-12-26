package com.ant.mms.service.impl;

import com.ant.mms.enums.ResultEnum;
import com.ant.mms.exception.KitcException;
import com.ant.mms.pojo.User;
import com.ant.mms.respository.UserRepository;
import com.ant.mms.service.PyramidService;
import com.ant.mms.vo.PyramidUserListVo;
import com.ant.mms.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by wolf   2018/12/23
 */
@Service
@Slf4j
public class PyramidServiceImpl implements PyramidService{

    @Autowired private UserRepository userRepository;

    //根据用户id查询其管理会员
    @Override
    public List<PyramidUserListVo> list(String UserId) {
        Optional<User> leader=userRepository.findById(UserId);
        if (!leader.isPresent())
            throw new KitcException(ResultEnum.USER_IS_NOT_EXIST);
        return this.getTreeGenerationMembers(leader.get().getId());
    }
    //获取一组用户的id
    private List<String> getInviteCodesByUserList(List<User>  list){
        return list.stream().map(user -> user.getId()).collect(Collectors.toList());
    }

    private  List<PyramidUserListVo> getTreeGenerationMembers(String userId){
        List<PyramidUserListVo> UserVoList = new ArrayList<>();
        List<User> group1 = userRepository.findUsersByLeaderIdIn(Arrays.asList(userId)) ;
        if (group1.size()==0){
            return UserVoList;
        }
        List<UserVo> groupVo1 =new ArrayList<>();
        group1.forEach(user -> {
            UserVo userVo =new UserVo();
            BeanUtils.copyProperties(user,userVo);
            groupVo1.add(userVo);
        });
        PyramidUserListVo pvo1 = new PyramidUserListVo();
        pvo1.setGeneration("一代会员");pvo1.setProductInfoVOList(groupVo1);
        UserVoList.add(pvo1);

        List<User> group2 = userRepository.findUsersByLeaderIdIn(getInviteCodesByUserList(group1)) ;
        if (group2.size()==0){
            return UserVoList;
        }
        List<UserVo> groupVo2 =new ArrayList<>();
        group2.forEach(user -> {
            UserVo userVo =new UserVo();
            BeanUtils.copyProperties(user,userVo);
            groupVo2.add(userVo);
        });
        PyramidUserListVo pvo2 = new PyramidUserListVo();
        pvo2.setGeneration("二代会员");pvo2.setProductInfoVOList(groupVo2);
        UserVoList.add(pvo2);
        List<User> group3 = userRepository.findUsersByLeaderIdIn(getInviteCodesByUserList(group2)) ;
        if (group3.size()==0){
            return UserVoList;
        }
        List<UserVo> groupVo3 =new ArrayList<>();
        group3.forEach(user -> {
            UserVo userVo =new UserVo();
            BeanUtils.copyProperties(user,userVo);
            groupVo3.add(userVo);
        });
        PyramidUserListVo pvo3 = new PyramidUserListVo();
        pvo3.setGeneration("三代会员");pvo3.setProductInfoVOList(groupVo3);
        UserVoList.add(pvo3);
        return UserVoList ;
    }
}
