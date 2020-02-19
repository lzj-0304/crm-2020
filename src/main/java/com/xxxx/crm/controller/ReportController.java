package com.xxxx.crm.controller;

import com.xxxx.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("report")
public class ReportController extends BaseController {


    @RequestMapping("{type}")
    public String index(@PathVariable Integer type){
        if(null !=type ){
            if(type ==0){
                return "report/customer_contri";
            }else if(type==1){
                return "report/customer_make";
            }else if(type==2){
                return "";
            }else if(type==3){
                return "report/customer_loss";
            }else{
                return "";
            }
        }else{
            return "";
        }
    }
}
