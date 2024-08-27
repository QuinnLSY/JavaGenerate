package com.java_generate.bean;

import com.java_generate.utils.PropertiesUtils;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-03-15:15
 * @ Description：基础设置
 */

public class Constants {
    public static String AUTHOR_COMMENT;

    public static Boolean IGNORE_TABLE_PREFIX;

    public static String SUFFIX_BEAN_QUERY;
    public static String SUFFIX_BEAN_QUERY_FUZZY;
    public static String SUFFIX_BEAN_QUERY_TIME_START;
    public static String SUFFIX_BEAN_QUERY_TIME_END;
    public static String SUFFIX_MAPPERS;

    public static String PATH_JAVA="java";
    public static String PATH_RESOURCES="resources";
    public static String PATH_BASE_s;
    public static String PATH_BASE;
    public static String PATH_PO;
    public static String PATH_QUERY;
    public static String PATH_VO;
    public static String PATH_UTILS;
    public static String PATH_ENUMS;
    public static String PATH_MAPPERS;
    public static String PATH_MAPPERS_XMLS;
    public static String PATH_SERVICE;
    public static String PATH_SERVICE_IMPL;

    public static String PACKAGE_BASE;
    public static String PACKAGE_PO;
    public static String PACKAGE_QUERY;
    public static String PACKAGE_VO;
    public static String PACKAGE_UTILS;
    public static String PACKAGE_ENUMS;
    public static String PACKAGE_MAPPERS;
    public static String PACKAGE_SERVICE;
    public static String PACKAGE_SERVICE_IMPL;

    // 需要忽略的属性
    public static String IGNORE_BEAN_TOJSON_FIELD;
    public static String IGNORE_BEAN_TOJSON_EXPRESSION;
    public static String IGNORE_BEAN_TOJSON_CLASS;
    // 日期序列化、反序列化
    public static String BEAN_DATE_FORMAT_EXPRESSION;
    public static String BEAN_DATE_FORMAT_CLASS;
    public static String BEAN_DATE_PARSE_EXPRESSION;
    public static String BEAN_DATE_PARSE_CLASS;

    static {
        AUTHOR_COMMENT = PropertiesUtils.getString("author.comment");
        // 忽略表格名称前缀
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        // Bean参数后缀，在BuildTable的BeanParamName方法中使用
        SUFFIX_BEAN_QUERY = PropertiesUtils.getString("suffix.bean.query");
        SUFFIX_BEAN_QUERY_FUZZY = PropertiesUtils.getString("suffix.bean.query.fuzzy");
        SUFFIX_BEAN_QUERY_TIME_START = PropertiesUtils.getString("suffix.bean.query.time.start");
        SUFFIX_BEAN_QUERY_TIME_END = PropertiesUtils.getString("suffix.bean.query.time.end");
        SUFFIX_MAPPERS = PropertiesUtils.getString("suffix.mappers");
        // 包路径
        PACKAGE_BASE = PropertiesUtils.getString("package.base");
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.po");
        PACKAGE_QUERY = PACKAGE_BASE + "." + PropertiesUtils.getString("package.query");
        PACKAGE_VO = PACKAGE_BASE + "." + PropertiesUtils.getString("package.vo");
        PACKAGE_UTILS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.utils");
        PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.enums");
        PACKAGE_MAPPERS = PACKAGE_BASE + "." + PropertiesUtils.getString("package.mappers");
        PACKAGE_SERVICE = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service");
        PACKAGE_SERVICE_IMPL = PACKAGE_BASE + "." + PropertiesUtils.getString("package.service.impl");
        // 文件路径
        PATH_BASE_s = PropertiesUtils.getString("path.base");
        PATH_BASE = PATH_BASE_s + "/" + PATH_JAVA;
        PATH_PO = PATH_BASE + "/" + PACKAGE_PO.replace(".", "/");
        PATH_QUERY = PATH_BASE + "/" + PACKAGE_QUERY.replace(".", "/");
        PATH_VO = PATH_BASE + "/" + PACKAGE_VO.replace(".", "/");
        PATH_UTILS = PATH_BASE + "/" + PACKAGE_UTILS.replace(".", "/");
        PATH_ENUMS = PATH_BASE + "/" + PACKAGE_ENUMS.replace(".", "/");
        PATH_MAPPERS = PATH_BASE + "/" + PACKAGE_MAPPERS.replace(".", "/");
        PATH_MAPPERS_XMLS = PATH_BASE_s + "/" + PATH_RESOURCES + "/" + PACKAGE_MAPPERS.replace(".", "/");
        PATH_SERVICE = PATH_BASE + "/" + PACKAGE_SERVICE.replace(".", "/");
        PATH_SERVICE_IMPL = PATH_BASE + "/" + PACKAGE_SERVICE_IMPL.replace(".", "/");


        IGNORE_BEAN_TOJSON_FIELD = PropertiesUtils.getString("ignore.bean.tojson.field");
        IGNORE_BEAN_TOJSON_EXPRESSION = PropertiesUtils.getString("ignore.bean.tojson.expression");
        IGNORE_BEAN_TOJSON_CLASS = PropertiesUtils.getString("ignore.bean.tojson.class");

        BEAN_DATE_FORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.format.expression");
        BEAN_DATE_FORMAT_CLASS = PropertiesUtils.getString("bean.date.format.class");
        BEAN_DATE_PARSE_EXPRESSION = PropertiesUtils.getString("bean.date.parse.expression");
        BEAN_DATE_PARSE_CLASS = PropertiesUtils.getString("bean.date.parse.class");

    }

    // sql数据类型与java语法数据类型相互转换
    public final static String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};
    public final static String[] SQL_DATE_TYPES = new String[]{"date"};
    public final static String[] SQL_BIG_DECIMAL_TYPES = new String[]{"decimal", "double", "float"};
    public final static String[] SQL_STRING_TYPES = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
    public final static String[] SQL_INTEGER_TYPES = new String[]{"int", "tinyint"};
    public final static String[] SQL_LONG_TYPES = new String[]{"bigint"};

    public static void main(String[] args){
        System.out.println(PATH_PO);
        System.out.println(PACKAGE_PO);
    }
}
