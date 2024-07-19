package com.example.blogdemo.domain.po;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName artical
 */
@TableName(value ="artical")
@Data
public class Artical implements Serializable {
    /**
     * 文章id
     */
    @TableId(value = "post_id",type = IdType.AUTO)
    private Long postId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String context;

    /**
     * 作者id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date lastModified;

    @TableLogic
    @TableField(value = "isDelete")
    private boolean isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}