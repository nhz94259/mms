package com.ant.mms.respository;



import com.ant.mms.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wolf
 */
public interface UserRepository extends JpaRepository<User,String> {

    User findUserByUsernameAndPassword(String username, String md5Password);

    List<User> findUserByUsername(String username);

    List<User> findUserByEmail(String email);
}
