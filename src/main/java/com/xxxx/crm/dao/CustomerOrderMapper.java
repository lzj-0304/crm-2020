package com.xxxx.crm.dao;


import com.xxxx.base.BaseMapper;
import com.xxxx.crm.vo.CustomerOrder;

import java.util.Map;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {


    public Map<String,Object> queryOrderDetailByOrderId(Integer orderId);

    public CustomerOrder  queryLastCustomerOrderByCusId(Integer cusId);

}