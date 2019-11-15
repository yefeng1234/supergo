package com.supergo.user.service;


import com.supergo.user.domain.User;

/**
 * Created by on 2019/5/29.
 */
public interface UserService {

    public User create(String username, String password);

}
