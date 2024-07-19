package com.example.blogdemo.domain.dto.artical;

import lombok.Data;

@Data
public class ArticalUpdateDTO {
    /**
     * 文章id
     */
    private Long postId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String context;



}
