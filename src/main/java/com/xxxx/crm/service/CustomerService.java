package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.base.BaseService;
import com.xxxx.crm.dao.CustomerLossMapper;
import com.xxxx.crm.dao.CustomerMapper;
import com.xxxx.crm.dao.CustomerOrderMapper;
import com.xxxx.crm.query.CustomerQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.Customer;
import com.xxxx.crm.vo.CustomerLoss;
import com.xxxx.crm.vo.CustomerOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@SuppressWarnings("all")
public class CustomerService extends BaseService<Customer,Integer> {

    @Autowired
    private CustomerMapper customerMapper;


    @Autowired
    private CustomerLossMapper customerLossMapper;


    @Autowired
    private CustomerOrderMapper customerOrderMapper;



    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCustomer(Customer customer){
        /**
         * 1.参数校验
         *    客户名称 name 非空  不可重复
         *    phone 联系电话  非空  格式符合规范
         *    法人  非空
         * 2.默认值设置
         *     isValid  state  cteaetDate  updadteDate
         *      khno 系统生成 唯一  (uuid| 时间戳 | 年月日时分秒  雪花算法)
         *3.执行添加  判断结果
         */
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        AssertUtil.isTrue(null!=customerMapper.queryCustomerByName(customer.getName()),"该客户已存在!");
        customer.setIsValid(1);
        customer.setState(0);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        String khno = "KH_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        customer.setKhno(khno);
        AssertUtil.isTrue(insertSelective(customer)<1,"客户添加失败!");
    }

    private void checkParams(String name, String phone, String fr) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"请指定客户名称!");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)),"手机号格式非法！");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"请指定公司法人!");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomer(Customer customer){
        /**
         * 1.参数校验
         *    记录存在校验
         *    客户名称 name 非空  不可重复
         *    phone 联系电话  非空  格式符合规范
         *    法人  非空
         * 2.默认值设置
         *      updadteDate
         *3.执行更新  判断结果
         */
        AssertUtil.isTrue(null==customer.getId()||null==selectByPrimaryKey(customer.getId()),"待更新记录不存在!");
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        Customer temp =customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(customer.getId())),"该客户已存在!");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户更新失败!");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomer(Integer cid){
        Customer customer= selectByPrimaryKey(cid);
        AssertUtil.isTrue(null==cid||null==cid,"待删除记录不存在!");
        /**
         * 如果客户被删除
         *     级联 客户联系人 客户交往记录 客户订单  被删除
         *
         * 如果客户被删除
         *     如果子表存在记录  不支持删除
         */
        customer.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户删除失败!");


    }



    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomerState(){
        List<Customer> lossCutomers = customerMapper.queryLossCustomers();
        if(null !=lossCutomers && lossCutomers.size()>0){
            List<CustomerLoss> customerLosses= new ArrayList<CustomerLoss>();
            List<Integer> lossCusIds=new ArrayList<Integer>();
            lossCutomers.forEach(customer->{
                CustomerLoss customerLoss=new CustomerLoss();
                // 设置最后下单时间
                CustomerOrder lastCustomerOrder = customerOrderMapper.queryLastCustomerOrderByCusId(customer.getId());
                if(null !=lastCustomerOrder){
                    customerLoss.setLastOrderTime(lastCustomerOrder.getOrderDate());
                }
                customerLoss.setCreateDate(new Date());
                customerLoss.setCusManager(customer.getCusManager());
                customerLoss.setCusName(customer.getName());
                customerLoss.setCusNo(customer.getKhno());
                customerLoss.setIsValid(1);
                //  设置客户流失状态为暂缓流失状态
                customerLoss.setState(0);
                customerLoss.setUpdateDate(new Date());
                customerLosses.add(customerLoss);
                lossCusIds.add(customer.getId());
            });
            AssertUtil.isTrue(customerLossMapper.insertBatch(customerLosses)<customerLosses.size(),"客户流失数据流转失败!");
            AssertUtil.isTrue(customerMapper.updateCustomerStateByIds(lossCusIds)<lossCusIds.size(),"客户流失数据流转失败!");
        }
    }



    public Map<String,Object> countCustomerMake(){
        Map<String,Object> result=new HashMap<String,Object>();
        List<Map<String,Object>> list =customerMapper.countCustomerMake();
        List<String> data1List=new ArrayList<String>();
        List<Integer> data2List=new ArrayList<Integer>();
        list.forEach(m->{
            data1List.add(m.get("level").toString());
            data2List.add(Integer.parseInt(m.get("total")+""));
        });
        result.put("data1",data1List);
        result.put("data2",data2List);
        return result;
    }


    public Map<String,Object> countCustomerMake02(){
        Map<String,Object> result=new HashMap<String,Object>();
        List<Map<String,Object>> list =customerMapper.countCustomerMake();
        List<String> data1List=new ArrayList<String>();
        List<Map<String,Object>> data2List=new ArrayList<Map<String,Object>>();
        list.forEach(m->{
            data1List.add(m.get("level").toString());
            Map<String,Object> temp=new HashMap<String,Object>();
            temp.put("name",m.get("level"));
            temp.put("value",m.get("total"));
            data2List.add(temp);
        });
        result.put("data1",data1List);
        result.put("data2",data2List);
        return result;
    }
}
