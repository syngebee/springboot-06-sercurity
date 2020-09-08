package com.cyc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //链式编程

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    //授权
    protected void configure(HttpSecurity http) throws Exception {
        //需求:首页所有人可以访问,功能页有权限才能访问
        //首先http.authorizeRequests()开启授权访问
        //.antMatchers添加一行条件
            //permitAll()代表所有人可以访问
            //hasRole()代表只有某个角色可以访问

        //请求授权的规则
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

        //没有权限跳转到首页
        http.formLogin()
                .loginPage("/toLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login");


        //注销
        http.csrf().disable();
        http.logout().logoutSuccessUrl("/");

        //开启记住我功能,默认保存两周
        http.rememberMe().rememberMeParameter("remember");
    }

    @Autowired
    private DataSource dataSource;
    @Override
    //认证
    //密码编码: PasswordEncoder
    //在SpringSecurity5.0+ 新增了许多的加密方式, 密码没有加密不让过
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //在内存中认证
/*        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("cyc").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2","vip3")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");*/
        //数据库中认证,这些数据正常应该从数据库中取
        auth
                .userDetailsService(myUserDetailService)
                .passwordEncoder(passwordEncoder());
    }
}
