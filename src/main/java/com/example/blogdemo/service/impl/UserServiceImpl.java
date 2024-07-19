package com.example.blogdemo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogdemo.common.ErrorCode;
import com.example.blogdemo.config.JwtProperties;
import com.example.blogdemo.domain.dto.user.LoginDTO;
import com.example.blogdemo.domain.dto.user.RegisterDTO;
import com.example.blogdemo.domain.po.User;
import com.example.blogdemo.domain.vo.UserVO;
import com.example.blogdemo.exception.BusinessException;
import com.example.blogdemo.mapper.UserMapper;
import com.example.blogdemo.service.UserService;
import com.example.blogdemo.utils.JwtTool;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


/**
* @author 18126
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-07-17 00:16:53
*/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final JwtTool jwtTool;

    private final JwtProperties jwtProperties;

    private final String SALT = "blog";

    @Override
    public Long register(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        String checkPassword = registerDTO.getCheckPassword();

        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password) || StrUtil.isEmpty(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (!password.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入的密码不一致");
        }

        User one = lambdaQuery().eq(User::getUserName, username).one();
        if (one != null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号已存在，请重新输入账号名" );
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        User user = new User();
        user.setUserName(username);
        user.setPassword(encryptPassword);
        boolean save = save(user);
        if (!save){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"用户注册失败！");
        }
        return user.getUserId();
    }

    @Override
    public UserVO login(LoginDTO loginDTO,HttpServletRequest request) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        User user = lambdaQuery().eq(User::getUserName, username).one();
        if (user == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        if (!encryptPassword.equals(user.getPassword())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }

        String token = jwtTool.createToken(user.getUserId(), jwtProperties.getTokenTTL());

        UserVO userVO = new UserVO();
        userVO.setUserId(user.getUserId());
        userVO.setUserName(user.getUserName());
        userVO.setEmail(user.getEmail());
        userVO.setCreated(user.getCreated());
        userVO.setLastModified(user.getLastModified());
        userVO.setToken(token);
        request.getSession().setAttribute("User_Login_Status",userVO);
        return userVO;
    }

    @Override
    public Long getCurrentUser(HttpServletRequest request) {
        Object user_login_status =  request.getSession().getAttribute("User_Login_Status");
        if (user_login_status == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        UserVO  userVO = (UserVO ) user_login_status;
        if (userVO == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        return userVO.getUserId();
    }
}




