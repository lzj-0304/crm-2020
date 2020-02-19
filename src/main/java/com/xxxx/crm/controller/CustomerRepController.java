package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.crm.model.ResultInfo;
import com.xxxx.crm.query.CustomerRepQuery;
import com.xxxx.crm.service.CustomerLossService;
import com.xxxx.crm.service.CustomerRepService;
import com.xxxx.crm.vo.Customer;
import com.xxxx.crm.vo.CustomerRep;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_rep")
public class CustomerRepController extends BaseController {


    @Resource
    private CustomerLossService customerLossService;

    @Resource
    private CustomerRepService customerRepService;

    @RequestMapping("index")
    public String index(Integer id, Model model){
        model.addAttribute("customerLoss",customerLossService.selectByPrimaryKey(id));
        return "customer/customer_rep";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerRepsByParams(CustomerRepQuery customerRepQuery){
        return customerRepService.queryByParamsForTable(customerRepQuery);
    }

    @RequestMapping("addOrUpdateCustomerRepPage")
    public String addOrUpdateCustomerRepPage(Integer lossId,Integer id,Model model){
        // 暂缓详情
        model.addAttribute("customerRep",customerRepService.selectByPrimaryKey(id));
        // 流失记录id
        model.addAttribute("lossId",lossId);
        return  "customer/customer_rep_add_update";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomerRep(CustomerRep customerRep){
        customerRepService.saveCustomerRep(customerRep);
        return success("暂缓记录添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerRep(CustomerRep customerRep){
        customerRepService.updateCustomerRep(customerRep);
        return success("暂缓记录更新成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomerRep(Integer id){
        customerRepService.deleteCustomerRep(id);
        return success("暂缓记录删除成功");
    }



}
