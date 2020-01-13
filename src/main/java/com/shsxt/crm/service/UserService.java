package com.shsxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("all")
public class UserService  extends BaseService<User,Integer> {



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





}
