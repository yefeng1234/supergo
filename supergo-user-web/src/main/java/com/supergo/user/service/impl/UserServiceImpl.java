package com.supergo.user.service.impl;


import com.supergo.user.domain.User;
import com.supergo.user.mapper.UserMapper;
import com.supergo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by on 2019/5/29.
 */
@Service
public class UserServiceImpl implements UserService {

    //注入mapper对象
    @Autowired
    private UserMapper userMapper;

    @Override
    public User create(String username, String password) {

        //创建对象
        User user = new User();
        user.setUsername(username);
        user.setPassword("{bcrypt}"+new BCryptPasswordEncoder().encode(password));

        //保存
        userMapper.insertSelective(user);

        return user;
    }
}
