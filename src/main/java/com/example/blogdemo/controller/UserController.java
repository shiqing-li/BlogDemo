package com.example.blogdemo.controller;

import com.example.blogdemo.common.BaseResponse;
import com.example.blogdemo.common.ResultUtils;
import com.example.blogdemo.domain.dto.user.LoginDTO;
import com.example.blogdemo.domain.dto.user.RegisterDTO;
import com.example.blogdemo.domain.vo.UserVO;
import com.example.blogdemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody RegisterDTO registerDTO) {
        return ResultUtils.success(userService.register(registerDTO));
    }

    @PostMapping("/login")
    public BaseResponse<UserVO> login(@RequestBody LoginDTO loginDTO,HttpServletRequest request) {
        return ResultUtils.success(userService.login(loginDTO,request));
    }

    @GetMapping("/me")
    public BaseResponse<Long> getCurrentUser(HttpServletRequest request){
        return ResultUtils.success(userService.getCurrentUser(request));
    }
}
