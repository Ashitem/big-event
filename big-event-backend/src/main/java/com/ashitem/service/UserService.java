package com.ashitem.service;

import com.ashitem.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    //根据用户名查询用户
    User findByUserName(String username);
    //注册
    void register(String username, String password);
    //更新
    void update(User user);
    //更新头像
    void updateAvatar(String avatarUrl);
    //修改密码
    void updatePwd(@Param("newPwd") String newPwd);
}
