package com.yu.usercenter.request;


import lombok.Data;



@Data
public class UserRegisterRequest {
   private String userAccount;
   private String password;
   private String checkPassword;
}
