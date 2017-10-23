package com.wlwx.dispatch.service.impl;

import com.wlwx.dispatch.entity.User;
import com.wlwx.dispatch.mapper.UserMapper;
import com.wlwx.dispatch.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImp implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public User loadUserByUsername(String username) {
        return userMapper.getUser(username);
    }
}
