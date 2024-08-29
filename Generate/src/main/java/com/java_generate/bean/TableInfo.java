package com.java_generate.bean;

import com.java_generate.bean.FieldInfo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-03-14:51
 * @ Description：表信息
 */

public class TableInfo {
    /**
    * 表名
     */
    private String tableName;
    /**
    * 类名
     */
    private String beanName;
    /**
     * 类参数名
     */
    private String beanParamName;
    /**
    * 表注释
     */
    private String tableComment;
    /**
    * 字段列表
     */
    private List<FieldInfo> fieldList;
    /**
    * 扩展字段列表
     */
    private List<FieldInfo> extendFieldList;
    /**
    * 唯一索引集合
     */
    private Map<String, List<FieldInfo>> keyIndexMap = new LinkedHashMap();
    /**
    * 是否包含Date类型
     */
    private Boolean haveDate = false;
    /**
     * 是否包含DataTime类型
     */
    private Boolean haveDataTime = false;
    /**
     * 是否包含BigDecimal类型
     */
    private Boolean haveBigDecimal = false;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanParamName() {
        return beanParamName;
    }

    public void setBeanParamName(String beanParamName) {
        this.beanParamName = beanParamName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<FieldInfo> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<FieldInfo> fieldList) {
        this.fieldList = fieldList;
    }

    public List<FieldInfo> getExtendFieldList() {
        return extendFieldList;
    }

    public void setExtendFieldList(List<FieldInfo> extendFieldList) {
        this.extendFieldList = extendFieldList;
    }

    public Map<String, List<FieldInfo>> getKeyIndexMap() {
        return keyIndexMap;
    }

    public void setKeyIndexMap(Map<String, List<FieldInfo>> keyIndexMap) {
        this.keyIndexMap = keyIndexMap;
    }

    public Boolean getHaveDate() {
        return haveDate;
    }

    public void setHaveDate(Boolean haveDate) {
        this.haveDate = haveDate;
    }

    public Boolean getHaveDataTime() {
        return haveDataTime;
    }

    public void setHaveDataTime(Boolean haveDataTime) {
        this.haveDataTime = haveDataTime;
    }

    public Boolean getHaveBigDecimal() {
        return haveBigDecimal;
    }

    public void setHaveBigDecimal(Boolean haveBigDecimal) {
        this.haveBigDecimal = haveBigDecimal;
    }
}
