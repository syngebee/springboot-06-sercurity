package com.cyc.service.impl;

import com.cyc.dto.UserDTO;
import com.cyc.mapper.UserMapper;
import com.cyc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }
}
