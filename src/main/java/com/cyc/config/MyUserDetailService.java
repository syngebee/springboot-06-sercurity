package com.cyc.config;

import com.cyc.dto.UserDTO;
import com.cyc.pojo.Role;
import com.cyc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.selectUserByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        Set<Role> roles = user.getRoles();
        //新建一个权限集合
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : roles) {
            // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole()));
        }

        //org.springframework.security.core.userdetails.User
        //构造方法User(String username, String password,Collection<? extends GrantedAuthority> authorities)
        //password不加密报错
        return new User(user.getUsername()
                ,new BCryptPasswordEncoder().encode(user.getPassword())
                ,grantedAuthorities);
    }
}
