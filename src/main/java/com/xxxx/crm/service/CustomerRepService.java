package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CustomerRep;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CustomerRepService extends BaseService<CustomerRep,Integer> {

    @Resource
    private CustomerLossService customerLossService;

    public void saveCustomerRep(CustomerRep customerRep){
        AssertUtil.isTrue(null ==customerLossService.selectByPrimaryKey(customerRep.getLossId()),
                "请指定流失记录!");
        AssertUtil.isTrue(StringUtils.isBlank(customerRep.getMeasure()),"请输入暂缓措施!");
        customerRep.setIsValid(1);
        customerRep.setCreateDate(new Date());
        customerRep.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(customerRep)<1,"暂缓记录添加失败!");
    }

    public void updateCustomerRep(CustomerRep customerRep){
        AssertUtil.isTrue(null ==customerLossService.selectByPrimaryKey(customerRep.getLossId()),
                "请指定流失记录!");
        AssertUtil.isTrue(StringUtils.isBlank(customerRep.getMeasure()),"请输入暂缓措施!");
        AssertUtil.isTrue(null == selectByPrimaryKey(customerRep.getId()),"待更新暂缓记录不存在!");
        customerRep.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerRep)<1,"暂缓记录添加失败!");
    }


    public void deleteCustomerRep(Integer id){
        CustomerRep customerRep = selectByPrimaryKey(id);
        AssertUtil.isTrue(null == customerRep,"待删除暂缓记录不存在!");
        customerRep.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerRep)<1,"暂缓记录删除失败!");
    }
}
