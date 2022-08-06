package com.yu.usercenter.controller;

import com.yu.usercenter.common.BaseResponse;
import com.yu.usercenter.common.ResultUtils;
import com.yu.usercenter.model.User;
import com.yu.usercenter.request.UserLoginRequest;
import com.yu.usercenter.request.UserRegisterRequest;
import com.yu.usercenter.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
@Resource
    UserService userService;
// 注册
@PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
    if(userRegisterRequest==null){
        return null;
    }
    String userAccount = userRegisterRequest.getUserAccount();
    String password = userRegisterRequest.getPassword();
    String checkPassword = userRegisterRequest.getCheckPassword();
    long date = userService.userRegister(userAccount, password, checkPassword);
    return ResultUtils.success(date);
}
// 登录
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
       if(userLoginRequest==null){
           return null;
       }
        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        User data = userService.userLogin(userAccount, password, request);
        return ResultUtils.success(data);
    }
// 查询
   @GetMapping("/search")
   public BaseResponse<List<User>> userSearch(String username,HttpServletRequest request){
       List<User> data = userService.userSearch(username, request);
       return ResultUtils.success(data);
   }
// 删除
   @PostMapping("delete")
   public boolean userDelete(@RequestBody String userAccount,HttpServletRequest request){
    return userService.userDelete(userAccount,request);
   } 
}
