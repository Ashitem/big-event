package com.ashitem.controller;

import com.ashitem.pojo.Result;
import com.ashitem.pojo.User;
import com.ashitem.service.UserService;
import com.ashitem.utils.JwtUtil;
import com.ashitem.utils.Md5Util;
import com.ashitem.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated //要添加否则Validation不生效
public class UserController {
    @Autowired
   private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户
       User user= userService.findByUserName(username);
       if (user==null){
           //没有占用
           //注册
           userService.register(username,password);
           return Result.success();
       }else {
           //占用
           return Result.error("该用户名已被占用！");
       }
    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        //根据用户名查询user
        User loginUser = userService.findByUserName(username);
        //判断是否查询到
        if (loginUser==null){
            return Result.error("用户名不存在！");

        }
        //判断密码是否正确 loginUser中的密码与md5加密后的password进行对比
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            //登录成功
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            //把token存储到redis中
            String token = JwtUtil.genToken(claims);
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("用户名或密码输入错误！");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token){
        //根据用户名查询用户
//        Map<String,Object> map=JwtUtil.parseToken(token);
//        String username= (String) map.get("username");
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User userInfo = userService.findByUserName(username);
        return Result.success(userInfo);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public  Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        //校验参数
        String oldPwd=params.get("old_pwd");
        String newPwd=params.get("new_pwd");
        String rePwd=params.get("re_pwd");

        //判断是否为空
        if (!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数！");
        }
        //判断newPwd和rePwd密码是否一样
        if (!rePwd.equals(newPwd)){
            return Result.error("两次密码填写不一致！");
        }
        //判断新旧密码是否重复
        if(oldPwd.equals(newPwd)){
            return Result.error("新旧密码填写不能相同！");
        }
        //原密码是否正确
        //调用userService根据用户名拿到密码，再和oldPwd比对
       Map<String,Object> map= ThreadLocalUtil.get();
        String username= (String) map.get("username");
        User userLogin = userService.findByUserName(username);
        if (!userLogin.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写不正确！");
        }
        //调用userService来改变密码
        userService.updatePwd(newPwd);
        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}
