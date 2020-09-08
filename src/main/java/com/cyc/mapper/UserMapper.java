package com.cyc.mapper;

import com.cyc.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    UserDTO selectUserByUsername(String username);
}
