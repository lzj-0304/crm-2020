package com.xxxx.crm.query;


import com.xxxx.base.BaseQuery;

public class SaleChanceQuery extends BaseQuery {
    // 客户名
    private String customerName;
    // 创建人
    private String createMan;
    // 分配状态
    private String state;

    // 开发状态
    private Integer devResult;


    // 分配人
    private Integer aggsinMan;

    public Integer getDevResult() {
        return devResult;
    }

    public void setDevResult(Integer devResult) {
        this.devResult = devResult;
    }

    public Integer getAggsinMan() {
        return aggsinMan;
    }

    public void setAggsinMan(Integer aggsinMan) {
        this.aggsinMan = aggsinMan;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
