package com.example.blogdemo.domain.dto.user;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String checkPassword;
}
