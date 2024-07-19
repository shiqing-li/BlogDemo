package com.example.blogdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.blogdemo.domain.dto.artical.ArticalDTO;
import com.example.blogdemo.domain.dto.artical.ArticalUpdateDTO;
import com.example.blogdemo.domain.po.Artical;
import com.example.blogdemo.domain.vo.UserVO;

/**
* @author 18126
* @description 针对表【artical】的数据库操作Service
* @createDate 2024-07-17 00:16:43
*/

public interface ArticalService extends IService<Artical> {

    Long createNewArtical(ArticalDTO articalDTO, Long userId);

    Page<Artical> getArticalByUid(Long uid,Page page);

    Artical getArticalDetailByid(Long id);

    boolean updateArticalById(ArticalUpdateDTO articalUpdateDTO, Long userId);

    boolean deleteArticalById(Long postId, UserVO userVO);
}
