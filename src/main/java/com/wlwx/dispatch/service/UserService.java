package com.wlwx.dispatch.service;


import com.wlwx.dispatch.entity.User;

public interface UserService {
    User loadUserByUsername(String username);
}
