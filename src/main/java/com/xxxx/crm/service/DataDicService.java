package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.DataDic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DataDicService extends BaseService<DataDic,Integer> {


    public void saveDataDic(DataDic dataDic){
        AssertUtil.isTrue(StringUtils.isBlank(dataDic.getDataDicName()),"请指定字典名!");
        AssertUtil.isTrue(StringUtils.isBlank(dataDic.getDataDicValue()),"请设置字典值!");
        dataDic.setCreateDate(new Date());
        dataDic.setUpdateDate(new Date());
        dataDic.setIsValid((byte)1);
        AssertUtil.isTrue(insertSelective(dataDic)<1,"记录添加失败!");
    }

    public void updateDataDic(DataDic dataDic){
        AssertUtil.isTrue(StringUtils.isBlank(dataDic.getDataDicName()),"请指定字典名!");
        AssertUtil.isTrue(StringUtils.isBlank(dataDic.getDataDicValue()),"请设置字典值!");
        dataDic.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(dataDic)<1,"记录更新失败!");
    }


    public void delDataDic(Integer id){
        DataDic dataDic =selectByPrimaryKey(id);
        AssertUtil.isTrue(null== dataDic,"待删除记录不存在!");
        dataDic.setIsValid((byte)0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(dataDic)<1,"记录删除失败!");
    }



}
