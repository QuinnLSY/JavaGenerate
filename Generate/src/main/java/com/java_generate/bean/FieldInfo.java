package com.java_generate.bean;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-03-15:02
 * @ Description：表内单项数据信息
 */

public class FieldInfo {
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 属性名称
     */
    private String propertyName;

    private String sqlType;
    /**
     * 字段类型
     */
    private String javaType;
    /**
     * 字段备注
     */
    private String comment;
    /**
     * 是否自增
     */
    private Boolean isAutoIncrement;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.isAutoIncrement = autoIncrement;
    }
}
