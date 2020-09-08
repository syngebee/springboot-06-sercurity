package com.cyc.controller;

import com.cyc.dto.UserDTO;
import com.cyc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get-user")
    public UserDTO getUser(String username){
        return userService.selectUserByUsername(username);
    }
}
