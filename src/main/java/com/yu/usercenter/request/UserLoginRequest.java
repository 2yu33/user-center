package com.yu.usercenter.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String userAccount;
    private String password;
}
