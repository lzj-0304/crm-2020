package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.CustomerOrderMapper;
import com.xxxx.crm.vo.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomerOrderService extends BaseService<CustomerOrder,Integer> {

    @Autowired
    private CustomerOrderMapper customerOrderMapper;


    public Map<String,Object> queryOrderDetailByOrderId(Integer orderId){
        return  customerOrderMapper.queryOrderDetailByOrderId(orderId);
    }
}
