package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.CustomerLossMapper;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CustomerLoss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {

    @Autowired
    private CustomerLossMapper customerLossMapper;
    public CustomerLoss queryCustomerLossByCusNo(String cusNo) {
       return  customerLossMapper.queryCustomerLossByCusNo(cusNo);
    }


    public  void updateCustomerLossState(Integer id,String reason){
        CustomerLoss customerLoss = customerLossMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id || null == customerLoss,"待更新的记录不存在");
        customerLoss.setConfirmLossTime(new Date());
        customerLoss.setState(1);
        customerLoss.setLossReason(reason);
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerLoss)<1,"流失记录更新失败!");
    }
}
