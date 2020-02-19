package com.xxxx.crm.query;


import com.xxxx.base.BaseQuery;

public class CustomerOrderQuery extends BaseQuery {
    private Integer cid;
    private String orderNo;
    private Integer state;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
