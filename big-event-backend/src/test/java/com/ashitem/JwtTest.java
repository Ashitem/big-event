package com.ashitem;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen() {
        Map<String, Object> claims=new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        //生成Jwt的代码
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*12))//添加过期时间
                .sign(Algorithm.HMAC256("ashitem"));//指定算法，配置密钥
        System.out.println(token);
    }
    @Test
    public void testParse(){
        //接收token
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MzA2NzMwMzV9.PKNG7sfy2D1_P9i0jwh9PjefwfB3siwdV75TsLB9ZNU";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("ashitem")).build();

        DecodedJWT verify = jwtVerifier.verify(token);
        Map<String, Claim> claim=verify.getClaims();
        System.out.println(claim.get("user"));
        //修改头部或载荷验证失败
        //密钥改了，验证失败
        //时间过期，验证失败
    }
}
