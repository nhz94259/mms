package com.ant.mms.respository;



import com.ant.mms.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by wolf
 */
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findUserByUsernameAndPassword(String username, String md5Password);

    List<User> findUserByUsername(String username);

    List<User> findUserByEmail(String email);

    List<User> findUsersByLeaderIdIn(List<String> leaderList);

    Optional<User> findByInviteCode(String code);
}
