package com.example.blogdemo.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blogdemo.common.BaseResponse;
import com.example.blogdemo.common.ErrorCode;
import com.example.blogdemo.common.ResultUtils;
import com.example.blogdemo.domain.dto.artical.ArticalDTO;
import com.example.blogdemo.domain.dto.artical.ArticalUpdateDTO;
import com.example.blogdemo.domain.po.Artical;
import com.example.blogdemo.domain.vo.UserVO;
import com.example.blogdemo.exception.BusinessException;
import com.example.blogdemo.service.ArticalService;
import com.example.blogdemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class ArticalController {

    private final ArticalService articalService;

    private final UserService userService;

    @PostMapping
    public BaseResponse<Long> createNewArtical(@RequestBody ArticalDTO articalDTO, HttpServletRequest request){
        Long currentUserId = userService.getCurrentUser(request);
        if (currentUserId == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return ResultUtils.success(articalService.createNewArtical(articalDTO,currentUserId));
    }

    @GetMapping()
    public BaseResponse<Page<Artical>> getArticalByUid(@RequestParam(value = "uid", required = true) Long uid,
                                                       @RequestParam(value = "page", defaultValue = "0") int pageNo,
                                                       @RequestParam(value = "size", defaultValue = "10") int pageSize,
                                                       @RequestParam(value = "isAsc", defaultValue = "true") boolean isAsc){
        if (uid == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Page<Artical> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("created");
        orderItem.setAsc(isAsc);
        page.addOrder(orderItem);
        page.setCurrent(pageNo);
        page.setSize(pageSize);

        return ResultUtils.success(articalService.getArticalByUid(uid,page));
    }

    @GetMapping("/{id}")
    public BaseResponse<Artical> getArticalDetailByid(@PathVariable("id") Long id){
        if (id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(articalService.getArticalDetailByid(id));
    }

    @PutMapping()
    public BaseResponse<Boolean> upadteArticalById(@RequestBody ArticalUpdateDTO articalUpdateDTO, HttpServletRequest request){
        Long currentUserId = userService.getCurrentUser(request);
        if (currentUserId == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        return ResultUtils.success(articalService.updateArticalById(articalUpdateDTO,currentUserId));
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteArticalById(@PathVariable("id") Long postId,HttpServletRequest request){
        UserVO userVO = (UserVO) request.getSession().getAttribute("User_Login_Status");

        if (postId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        if (userVO == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        return ResultUtils.success(articalService.deleteArticalById(postId,userVO));
    }
}
