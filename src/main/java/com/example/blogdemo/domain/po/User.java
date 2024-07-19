package com.example.blogdemo.domain.po;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 账号
     */
    @TableField(value = "username")
    private String userName;

    /**
     * 密码
     */
    private String password;

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

    @TableLogic
    @TableField(value = "isDelete")
    private boolean isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}