package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {


    public int countPermissionByRoleId(Integer roleId);

    public int deletePermissionsByRoleId(Integer roleId);

    List<Integer>  queryRoleHasAllModuleIdsByRoleId(Integer roleId);

    List<String>  queryUserHasRolesHasPermissions(Integer userId);

    int  countPermissionsByModuleId(Integer mid);
    public  int deletePermissionsByModuleId(Integer mid);
}