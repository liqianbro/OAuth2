package com.oauth.pojo.vo;

import lombok.Data;

/**
 * @Author Lee
 * @date 2021/3/16 9:40 上午
 */
@Data
public class LoginVO {
    private String username;
    private String password;
    private String code;
}
