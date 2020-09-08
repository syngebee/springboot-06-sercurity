package com.cyc.service;

import com.cyc.dto.UserDTO;

public interface UserService {
    UserDTO selectUserByUsername(String username);
}
