package com.yu.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yu.usercenter.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 余某某
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2022-07-18 10:58:07
*/
public interface UserService extends IService<User> {
    /**
     *
     * @param userAccount  账号
     * @param password    密码
     * @param checkPassword 校验密码
     * @return  用户id
     */
    /**
     * 注册和登录接口
     */
    long userRegister(String userAccount,String password,String checkPassword);
    User userLogin(String userAccount, String password, HttpServletRequest request);
    List<User>userSearch(String username,HttpServletRequest request);
    boolean userDelete(String userAccount,HttpServletRequest request);
}
