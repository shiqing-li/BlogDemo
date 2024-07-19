package com.example.blogdemo.interceptor;

import com.example.blogdemo.common.UserContext;
import com.example.blogdemo.domain.vo.UserVO;
import com.example.blogdemo.utils.JwtTool;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTool jwtTool;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        1、后去请求头中的token
        String token = request.getHeader("authorization");
        UserVO userVO = (UserVO) request.getSession().getAttribute("User_Login_Status");
//        2、校验token
        Long userId = jwtTool.parseToken(userVO.getToken());
//        3、存入上下文
        UserContext.setUser(userId);
//        4、放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }
}
