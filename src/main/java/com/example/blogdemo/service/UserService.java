package com.example.blogdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogdemo.domain.dto.user.LoginDTO;
import com.example.blogdemo.domain.dto.user.RegisterDTO;
import com.example.blogdemo.domain.po.User;
import com.example.blogdemo.domain.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 18126
* @description 针对表【user】的数据库操作Service
* @createDate 2024-07-17 00:16:53
*/

public interface UserService extends IService<User> {


    Long register(RegisterDTO registerDTO);

    UserVO login(LoginDTO loginDTO,HttpServletRequest request);

    Long getCurrentUser(HttpServletRequest request);

}
