package com.xxxx.crm.service;

import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.SaleChanceMapper;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CusDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {

    @Autowired
    private SaleChanceMapper saleChanceMapper;

    public void saveCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 1.参数校验
         *     营销机会id 非空 记录必须存在
         *     计划项内容  非空
         *     计划项时间 非空
         * 2.参数默认值设置
         *    is_valid createDate  updateDate
         *
         *  3.执行添加  判断结果
         */
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());

        AssertUtil.isTrue(insertSelective(cusDevPlan)<1,"计划项记录添加失败!");
    }

    private void checkParams(Integer saleChanceId, String planItem, Date planDate) {
        AssertUtil.isTrue(null==saleChanceId || null == saleChanceMapper.selectByPrimaryKey(saleChanceId),"请设置营销机会id");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"请输入计划项内容!");
        AssertUtil.isTrue(null == planDate,"请指定计划项日期!");
    }


    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        /**
         * 1.参数校验
         *     id  非空 记录存在
         *     营销机会id 非空 记录必须存在
         *     计划项内容  非空
         *     计划项时间 非空
         * 2.参数默认值设置
             updateDate
         *  3.执行更新  判断结果
         */
        AssertUtil.isTrue(null==cusDevPlan.getId() || null == selectByPrimaryKey(cusDevPlan.getId()),"待更新记录不存在!");
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划项记录更新失败!");
    }

    public void delCusDevPlan(Integer id){
        CusDevPlan cusDevPlan =selectByPrimaryKey(id);
        AssertUtil.isTrue(null==id || null ==cusDevPlan ,"待删除记录不存在!");
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划项记录删除失败!");
    }




}
