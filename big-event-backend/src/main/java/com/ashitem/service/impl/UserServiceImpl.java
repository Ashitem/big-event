package com.ashitem.service.impl;

import com.ashitem.mapper.UserMapper;
import com.ashitem.pojo.User;
import com.ashitem.service.UserService;
import com.ashitem.utils.Md5Util;
import com.ashitem.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
       User user= userMapper.findByUserName(username);
        return user;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5Password = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username,md5Password);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void updatePwd(String newPwd) {
        String md5Password = Md5Util.getMD5String(newPwd);
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer id= (Integer) map.get("id");
        userMapper.updatePwd(md5Password,id);
    }
}
