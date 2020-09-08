package com.cyc.dto;

import com.cyc.pojo.Role;
import com.cyc.pojo.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
//多对多关系不要使用@Data,toString会互相调用,导致死循环
public class UserDTO extends User {
    Set<Role> roles;
}
