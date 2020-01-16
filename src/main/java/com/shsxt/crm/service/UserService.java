package com.shsxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.PhoneUtil;
import com.shsxt.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@SuppressWarnings("all")
public class UserService  extends BaseService<User,Integer> {


    @Autowired
    private UserMapper userMapper;

    public Map<String,Object> userList(UserQuery userQuery){
        Map<String,Object> result = new HashMap<String,Object>();
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        PageInfo<User> pageInfo =new PageInfo<User>(selectByParams(userQuery));
        result.put("count",pageInfo.getTotal());
        result.put("data",pageInfo.getList());
        result.put("code",0);
        result.put("msg","");
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        /**
         * 1.参数校验
         *    userId  非空  记录必须存在
         *    oldPassword  非空  必须与数据库一致
         *    newPassword 非空   新密码不能与原始密码相同
         *    confirmPassword 非空  与新密码必须一致
         * 2.设置用户新密码
         *     新密码加密
         * 3.执行更新
         */
        checkParams(userId, oldPassword, newPassword, confirmPassword);
        User user = selectByPrimaryKey(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(updateByPrimaryKeySelective(user) < 1, "密码更新失败!");
    }

    private void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        User user = selectByPrimaryKey(userId);
        AssertUtil.isTrue(null == userId || null == user, "用户未登录或不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "请输入原始密码!");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "请输入新密码!");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword), "请输入确认密码!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)), "新密码输入不一致!");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))), "原始密码不正确!");
        AssertUtil.isTrue(oldPassword.equals(newPassword), "新密码不能与旧密码相同!");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user) {
        /**
         * 1.参数校验
         *     用户名  非空   唯一
         *     email  非空  格式合法
         *     手机号 非空  格式合法
         * 2.设置默认参数
         *      isValid 1
         *      createDate   uddateDate
         *      userPwd   123456->md5加密
         * 3.执行添加  判断结果
         */
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        User temp = userMapper.queryUserByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp && (temp.getIsValid() == 1), "该用户已存在!");
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(insertSelective(user) == null, "用户添加失败!");


    }




    private void checkParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(email), "请输入邮箱地址!");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)), "手机号格式不合法!");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        /**
         * 1.参数校验
         *     id 非空  记录必须存在
         *     用户名  非空   唯一
         *     email  非空  格式合法
         *     手机号 非空  格式合法
         * 2.设置默认参数
         *        uddateDate
         * 3.执行更新  判断结果
         */
        AssertUtil.isTrue(null == user.getId() || null == selectByPrimaryKey(user.getId()), "待更新记录不存在!");
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        User temp = userMapper.queryUserByUserName(user.getUserName());
        if (null != temp && temp.getIsValid() == 1) {
            AssertUtil.isTrue(!(user.getId().equals(temp.getId())), "该用户已存在!");
        }
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(user) < 1, "用户更新失败!");
    }

    public void deleteUser(Integer userId) {
        User user = selectByPrimaryKey(userId);
        AssertUtil.isTrue(null == userId || null == user, "待删除记录不存在!");
        user.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(user) < 1, "用户记录删除失败!");

    }



}
