package com.xxxx.crm.dao;

import com.xxxx.base.BaseMapper;
import com.xxxx.crm.dto.ModuleDto;
import com.xxxx.crm.dto.TreeDto;
import com.xxxx.crm.vo.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    public List<TreeDto> queryAllModules();

    public List<Module> queryModules();

    Module  queryModuleByGradeAndModuleName(@Param("grade") Integer grade, @Param("moduleName") String moduleName);

    Module  queryModuleByGradeAndUrl(@Param("grade") Integer grade, @Param("url") String url);

    Module  queryModuleByOptValue(String optValue);

    List<Map<String, Object>>  queryAllModulesByGrade(Integer grade);

    int  countSubModuleByParentId(Integer mid);

    public List<ModuleDto> queryUserHasRoleHasModuleDtos(@Param("userId") Integer userId, @Param("grade") Integer grade, @Param("parentId") Integer parentId);
}