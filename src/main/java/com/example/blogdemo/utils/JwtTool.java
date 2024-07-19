package com.example.blogdemo.utils;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.example.blogdemo.common.ErrorCode;
import com.example.blogdemo.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTool {
    private final JWTSigner jwtSigner;

    public JwtTool(KeyPair keyPair){
        this.jwtSigner = JWTSignerUtil.createSigner("rs256",keyPair);
    }


    public String createToken(Long userId, Duration ttl){
        return JWT.create()
                .setPayload("user",userId)
                .setExpiresAt(new Date((System.currentTimeMillis() + ttl.toMillis())))
                .setSigner(jwtSigner)
                .sign();
    }

    public Long parseToken(String token){
        // 1、校验token是否为空
        if (token == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        JWT jwt;
        try {
            jwt = JWT.of(token).setSigner(jwtSigner);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"无效的token" + e);
        }
        // 2、校验并解析token
        if (!jwt.verify()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"无效的token" );
        }
        // 3、校验是否过期
        try {
            JWTValidator.of(jwt).validateDate();
        } catch (ValidateException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"token已过期" + e);
        }

        // 4、数据格式校验
        Object user = jwt.getPayload("user");
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"无效的token" );
        }

        // 5、数据解析
        try {
            return Long.valueOf(user.toString());
        } catch (NumberFormatException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"无效的token" + e );
        }

    }
}
