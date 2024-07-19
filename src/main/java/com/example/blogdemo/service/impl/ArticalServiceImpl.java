package com.example.blogdemo.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.blogdemo.common.ErrorCode;
import com.example.blogdemo.domain.dto.artical.ArticalDTO;
import com.example.blogdemo.domain.dto.artical.ArticalUpdateDTO;
import com.example.blogdemo.domain.po.Artical;
import com.example.blogdemo.domain.vo.UserVO;
import com.example.blogdemo.exception.BusinessException;
import com.example.blogdemo.mapper.ArticalMapper;
import com.example.blogdemo.service.ArticalService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 18126
* @description 针对表【artical】的数据库操作Service实现
* @createDate 2024-07-17 00:16:43
*/
@Service
public class ArticalServiceImpl extends ServiceImpl<ArticalMapper, Artical>
    implements ArticalService {

    @Override
    public Long createNewArtical(ArticalDTO articalDTO, Long userId) {

        if (articalDTO == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String title = articalDTO.getTitle();
        String context = articalDTO.getContext();

        Artical artical = new Artical();
        if (!StrUtil.isEmpty(title)){
            artical.setTitle(title);
        }

        if (!StrUtil.isEmpty(context)){
            artical.setContext(context);
        }

        artical.setCreated(new Date());
        artical.setLastModified(new Date());
        artical.setUserId(userId);
        boolean save = save(artical);
        if (!save){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"新增文章失败！");
        }

        return artical.getPostId();
    }

    @Override
    public Page<Artical> getArticalByUid(Long uid, Page page) {
        QueryWrapper<Artical> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",uid);
        Page<Artical> pages = lambdaQuery().eq(Artical::getUserId,uid).page(page);
        if (CollUtil.isEmpty(pages.getRecords())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"通过用户id查询文章失败");
        }
        return pages;
    }

    @Override
    public Artical getArticalDetailByid(Long id) {
        Artical one = getById(id);
        if (one == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"查询文章失败");
        }
        return one;
    }

    @Override
    public boolean updateArticalById(ArticalUpdateDTO articalUpdateDTO, Long userId) {
        if (articalUpdateDTO == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Long postId = articalUpdateDTO.getPostId();
        if (postId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Artical articalById = getById(postId);
        if (articalById == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"文章不存在");
        }

        if (!userId.equals(articalById.getUserId())){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        String title = articalUpdateDTO.getTitle();
        String context = articalUpdateDTO.getContext();
        QueryWrapper<Artical> wrapper = new QueryWrapper<>();

        Artical artical = new Artical();
        artical.setPostId(postId);
        if (!StrUtil.isEmpty(title)){
            artical.setTitle(title);
        }

        if (!StrUtil.isEmpty(context)){
            artical.setContext(context);
        }
        artical.setLastModified(new Date());

        wrapper.eq("last_modified",new Date());
        boolean update = updateById(artical);
        if (!update){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"文章修改失败！");
        }

        return true;
    }

    @Override
    public boolean deleteArticalById(Long postId, UserVO userVO) {
        Artical artical = getById(postId);
        if (artical == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"文章不存在");
        }

        if (!artical.getUserId().equals(userVO.getUserId())){
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        boolean remove = removeById(postId);
        if (!remove){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"删除文章失败！");
        }

        return true;
    }
}




