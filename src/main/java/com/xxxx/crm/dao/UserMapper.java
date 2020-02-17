package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.User;

public interface UserMapper extends BaseMapper<User,Integer> {

    User  queryUserByUserName(String userName);
}