package com.example.blogdemo.domain.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value ="user")
@Data
public class UserVO implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 账号
     */
    private String userName;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date lastModified;

    /**
     * token
     */
    private String token;


    private static final long serialVersionUID = 1L;
}
