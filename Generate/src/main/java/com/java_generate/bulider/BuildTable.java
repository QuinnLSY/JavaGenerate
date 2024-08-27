package com.java_generate.bulider;

import com.java_generate.bean.Constants;
import com.java_generate.bean.FieldInfo;
import com.java_generate.bean.TableInfo;
import com.java_generate.utils.JsonUtiles;
import com.java_generate.utils.PropertiesUtils;
import com.java_generate.utils.StringUtiles;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-01-22:58
 * @ Description：读取并构建tableInfo->TableInfo.java
 */

public class BuildTable {
    private  static final Logger logger = LoggerFactory.getLogger(BuildTable.class);
    private static Connection conn = null;
    private static String SQL_SHOW_TABLE_STATUS = "SHOW TABLE STATUS LIKE 'tb_product_info'";
    private static String SQL_SHOW_TABLE_FIELDS = "SHOW FULL FIELDS FROM %s";
    private static String SQL_SHOW_TABLE_INDEXS = "SHOW INDEX FROM %s";

    static {
        String driverName = PropertiesUtils.getString("db.driver.name");
        String url = PropertiesUtils.getString("db.url");
        String user = PropertiesUtils.getString("db.username");
        String password = PropertiesUtils.getString("db.password");
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, user, password);
        }catch (Exception e){
            logger.error("数据库连接失败", e);
        }
    }

    public static List<TableInfo> getTables() {
        PreparedStatement ps = null;
        ResultSet tableResult = null;

        List<TableInfo> tableInfoList = new ArrayList();
        try {
            ps = conn.prepareStatement(SQL_SHOW_TABLE_STATUS);
            tableResult = ps.executeQuery();
            while (tableResult.next()){
                String tableName = tableResult.getString("Name");
                String tableComment = tableResult.getString("Comment");
                // logger.info("tableName:{}, tableComment:{}",tableName,tableComment);

                String beanName = tableName;
                if (Constants.IGNORE_TABLE_PREFIX){
                    // 忽略表的第一个'_'前缀
                    beanName = tableName.substring(beanName.indexOf("_") + 1);
                }
                beanName = processfield(beanName, true);

                TableInfo tableInfo = new TableInfo();
                tableInfo.setTableName(tableName);
                tableInfo.setTableComment(tableComment);
                tableInfo.setBeanName(beanName);
                tableInfo.setBeanParamName(beanName+Constants.SUFFIX_BEAN_QUERY);

                readfieldInfo(tableInfo);
                getKeyIndexInfo(tableInfo);
                tableInfoList.add(tableInfo);
                logger.info(JsonUtiles.convertObj2Json(tableInfo));
            }
        }catch (Exception e){
            logger.error("读取表失败");
        }finally {
            if (tableResult != null){
                try {
                    tableResult.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (conn != null){
                try {
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return tableInfoList;
    }
    
    private static void readfieldInfo(TableInfo tableInfo){
        PreparedStatement ps = null;
        ResultSet fieldResult = null;

        List<FieldInfo> fieldInfoList = new ArrayList();
        try {
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_FIELDS, tableInfo.getTableName()));
            fieldResult = ps.executeQuery();
            while (fieldResult.next()){
                // 拿到属性名
                String field = fieldResult.getString("field");
                String type = fieldResult.getString("type");
                String extra = fieldResult.getString("extra");
                String comment = fieldResult.getString("comment");
                // 去掉type后面显示字符长度的括号
                if(type.indexOf("(") > 0){
                    type = type.substring(0, type.indexOf("("));
                }
                // 将属性名转换为驼峰命名
                String propertyName = processfield(field, false);
                // 构建fieldInfo
                FieldInfo fieldInfo = new FieldInfo();
                fieldInfoList.add(fieldInfo);

                fieldInfo.setFieldName(field);
                fieldInfo.setComment(comment);
                fieldInfo.setSqlType(type);
                fieldInfo.setAutoIncrement("auto_increment".equalsIgnoreCase(extra) ? true : false);
                fieldInfo.setPropertyName(propertyName);
                fieldInfo.setJavaType(processJavaType(type));
                // 判断是否包含时间、超精度类型
                if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, type)) {
                    tableInfo.setHaveDataTime(true);
                }
                if(ArrayUtils.contains(Constants.SQL_DATE_TYPES, type)){
                    tableInfo.setHaveDate(true);
                }
                if(ArrayUtils.contains(Constants.SQL_BIG_DECIMAL_TYPES, type)){
                    tableInfo.setHaveBigDecimal(true);
                }

//                logger.info("field:{}, type:{}, extra:{}, comment:{} \n properName:{}, javaType:{}, autoIncrement:{}, comment:{}", field, type, extra, comment, propertyName, fieldInfo.getJavaType(), fieldInfo.isAutoIncrement(), fieldInfo.getComment());
            }
            tableInfo.setFieldList(fieldInfoList);
        }catch (Exception e){
            logger.error("读取表项失败");
        }finally {
            if (fieldResult != null){
                try {
                    fieldResult.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<FieldInfo> getKeyIndexInfo(TableInfo tableInfo){
        PreparedStatement ps = null;
        ResultSet fieldResult = null;

        List<FieldInfo> fieldInfoList = new ArrayList();
        try {
            Map<String, FieldInfo> tempMap = new HashMap();
            for(FieldInfo fieldInfo : tableInfo.getFieldList()){
                tempMap.put(fieldInfo.getFieldName(), fieldInfo);  // columnName=filedName
            }
            ps = conn.prepareStatement(String.format(SQL_SHOW_TABLE_INDEXS, tableInfo.getTableName()));
            fieldResult = ps.executeQuery();
            while (fieldResult.next()){
                String keyName = fieldResult.getString("key_name");
                Integer nonUnique = fieldResult.getInt("non_unique");
                String columnName = fieldResult.getString("column_name");
                if(nonUnique == 1){
                    continue;
                }
                List<FieldInfo> keyFieldList = tableInfo.getKeyIndexMap().get(keyName);
                // 如果当前键名
                if(keyFieldList == null){
                    keyFieldList = new ArrayList();
                    tableInfo.getKeyIndexMap().put(keyName, keyFieldList);
                }
                //用一个tempMap在外循环一次获得所有字段名，在while里取一次就可以，不需要每次都for循环
                /*for(FieldInfo fieldInfo : tableInfo.getFieldList()){
                    if(columnName.equals(fieldInfo.getFieldName())){
                        keyFieldList.add(fieldInfo);
                    }
                }*/
                keyFieldList.add(tempMap.get(columnName));
            }
        }catch (Exception e){
            logger.error("读取表唯一索引失败");
        }finally {
            if (fieldResult != null){
                try {
                    fieldResult.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (ps != null){
                try {
                    ps.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return fieldInfoList;
    }

    // 处理字段,将表名转为驼峰命名
    private static String processfield(String field, Boolean upperCaseFirstLetter){
        StringBuffer sb = new StringBuffer();
        String[] fields = field.split("_");
        // 首单词判断需不需要大写
        sb.append(upperCaseFirstLetter? StringUtiles.toUpperCaseFirstOne(fields[0]) : fields[0]);
        // 后面的单词全都默认改大写
        for(int i = 1, len = fields.length; i < len; i++){
            sb.append(StringUtiles.toUpperCaseFirstOne(fields[i]));
        }
        return sb.toString();
    }

    private static String processJavaType(String type){
        if(ArrayUtils.contains(Constants.SQL_INTEGER_TYPES, type)){
            return "Integer";
        } else if (ArrayUtils.contains(Constants.SQL_STRING_TYPES,type)) {
            return "String";
        }else if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, type) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES,type)){
            return "Date";
        }else if (ArrayUtils.contains(Constants.SQL_BIG_DECIMAL_TYPES,type)){
            return "BigDecimal";
        }else if (ArrayUtils.contains(Constants.SQL_LONG_TYPES,type)){
            return "Long";
        }else{
            throw new RuntimeException("不支持的类型:" + type);
        }
    }
}
