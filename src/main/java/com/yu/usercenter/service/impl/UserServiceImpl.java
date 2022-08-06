package com.yu.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.usercenter.contant.UserConstant;
import com.yu.usercenter.service.UserService;
import com.yu.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import com.yu.usercenter.model.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
* @author 余某某
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2022-07-18 10:58:07
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    UserMapper userMapper;
    private static final String SALT ="niuBi";
    private static final String USER_LOGIN_START="user_Login_start";

    /**
     * 注册接口
     * @param userAccount  用户账号
     * @param password     密码
     * @param checkPassword 确认密码
     * @return 返回登录状态 0 失败
     */

    @Override
    public long userRegister(String userAccount, String password, String checkPassword) {
        /**
         * 1、校验
         */
        // 非空
        if(StringUtils.isAllBlank(userAccount,password,checkPassword)){
            return -1;
        }
        // 长度限制
        if(password.length()<=4||checkPassword.length()<=4){
            return -1;
        }
        // 密码和校验密码相同
        if(!(password.equals(checkPassword))){
            return -1;
        }
        // 不能重复账号
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        int count = this.count(queryWrapper);
        if(count>0){
            return -1;
        }

        // 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        // 存入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean result = this.save(user);
        if(!result){
            return -1;
        }
        return user.getId();

    }

    /**
     * 登录接口
     * @param userAccount   账号
     * @param password      密码
     * @return              用户信息
     */
    @Override
    public User userLogin(String userAccount, String password, HttpServletRequest request) {


        // 判空
        boolean result = StringUtils.isAllBlank(userAccount, password);
        if(result){
            return null;
        }
        // 判断登录信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount",userAccount);
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null){
            log.info("userAccount match password is failed");
            return null;
        }
        // 脱敏
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setColumn_4(user.getColumn_4());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUpdateTime(user.getUpdateTime());
        safetyUser.setUserRole(0);
        // 存储 session
        request.getSession().setAttribute(USER_LOGIN_START,safetyUser);
        System.out.println(safetyUser.getId());
        return safetyUser;
    }

    /**
     * 管理员查找用户
     * @param username 用户名
     * @param request  request
     * @return 查询结果 list集合
     */
    @Override
    public List<User> userSearch(String username,HttpServletRequest request) {
        /**
         * 鉴权
         */
        if(!isAdmin(request)){
            return new ArrayList<User>();
        }
        QueryWrapper<User>queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public boolean userDelete(String userAccount, HttpServletRequest request) {
        if(!isAdmin(request)){
            return false;
        }
        if(userAccount == null){
            return false;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userAccount",userAccount);
        return this.remove(queryWrapper);
    }
    private boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_START);
        User user = (User) userObj;
        if(user!=null && user.getUserRole()!= UserConstant.ADMIN_ROLE){
            return false;
        }
        return true;
    }
}




