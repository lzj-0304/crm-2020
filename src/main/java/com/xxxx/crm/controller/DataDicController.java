package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import com.xxxx.crm.model.ResultInfo;
import com.xxxx.crm.query.DataDicQuery;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.DataDicService;
import com.xxxx.crm.vo.DataDic;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("data_dic")
public class DataDicController extends BaseController {

    @Resource
    private DataDicService dataDicService;


    @RequestMapping("index")
    public String index(){
        return "dataDic/data_dic";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> dataDicList(DataDicQuery dataDicQuery){
        return dataDicService.queryByParamsForTable(dataDicQuery);
    }


    @RequestMapping("addOrUpdateDataDicPage")
    public String addOrUpdateDataDicPage(Integer id, Model model){
        model.addAttribute("dataDic",dataDicService.selectByPrimaryKey(id));
        return "dataDic/add_update";
    }


    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveDataDic(DataDic dataDic){
        dataDicService.saveDataDic(dataDic);
        return success("字典记录添加成功");
    }


    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateDataDic(DataDic dataDic){
        dataDicService.updateDataDic(dataDic);
        return success("字典记录更新成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteDataDic(Integer id){
        dataDicService.delDataDic(id);
        return success("字典记录删除成功");
    }




}
