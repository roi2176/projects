package com.swjtu.service;

import com.swjtu.entity.User;
import com.swjtu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(Integer id){
        return userMapper.findUserById(id);
    }
}
