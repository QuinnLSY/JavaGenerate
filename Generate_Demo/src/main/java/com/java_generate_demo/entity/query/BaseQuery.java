package com.java_generate_demo.entity.query;


public class BaseQuery{
    private SimplePage simplePage;
    private Integer pageSize;
    private Integer pageNo;
    private String orderBy;
    public SimplePage getSimplePage() { return simplePage; }

    public void setSimplePage(SimplePage simplePage) { this.simplePage = simplePage; }

    public Integer getPageSize() { return pageSize; }

    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }

    public Integer getPageNo() { return pageNo; }

    public void setPageNo(Integer pageNo) { this.pageNo = pageNo; }

    public String getOrderBy() { return this.orderBy; }

    public void setOrderBy(String orderBy) { this.orderBy = orderBy; }
}
