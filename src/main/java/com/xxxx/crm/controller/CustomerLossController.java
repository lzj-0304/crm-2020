package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.crm.annotaions.RequirePermission;
import com.xxxx.crm.model.ResultInfo;
import com.xxxx.crm.query.CustomerLossQuery;
import com.xxxx.crm.service.CustomerLossService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {
    @Resource
    private CustomerLossService customerLossService;


    @RequestMapping("index")
    private String index(){
        return "customerLoss/customer_loss";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerLossByParams(CustomerLossQuery customerLossQuery){
        return customerLossService.queryByParamsForTable(customerLossQuery);
    }


    @RequestMapping("updateCustomerLossState")
    @ResponseBody
    public ResultInfo updateCustomerLossState(Integer id,String reason){
        customerLossService.updateCustomerLossState(id,reason);
        return success("流失状态更新成功");
    }


}
