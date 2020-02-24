package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.crm.model.ResultInfo;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private UserService userService;

    @Autowired
    private  HttpServletRequest request;

    @RequestMapping("index")
    public String index(){
        return "saleChance/sale_chance";
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChancesByParams(Integer flag,HttpServletRequest request,SaleChanceQuery saleChanceQuery){
        if(null !=flag &&flag==1){
            // 查询分配给当前登录用户 营销记录
            saleChanceQuery.setAggsinMan(LoginUserUtil.releaseUserIdFromCookie(request));
        }
        return  saleChanceService.queryByParamsForTable(saleChanceQuery);
    }


    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(Integer id, Model model){
        if(null !=id){
            model.addAttribute("saleChance",saleChanceService.selectByPrimaryKey(id));
        }
        return "saleChance/add_update";
    }



    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveSaleChance(SaleChance saleChance){
        saleChance.setCreateMan(userService.selectByPrimaryKey(LoginUserUtil.releaseUserIdFromCookie(request)).getTrueName());
        saleChanceService.saveSaleChance(saleChance);
        return success("机会数据添加成功");
    }


    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success("机会数据更新成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteSaleChancesByIds(ids);
        return success("机会数据删除成功");
    }

    @RequestMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return success("开发状态更新成功");
    }

}
