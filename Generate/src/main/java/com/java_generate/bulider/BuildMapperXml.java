package com.java_generate.bulider;

import com.java_generate.bean.Constants;
import com.java_generate.bean.FieldInfo;
import com.java_generate.bean.TableInfo;
import com.java_generate.utils.StringUtiles;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-06-20:48
 * @ Description：构建mapper的xml
 */

public class BuildMapperXml {
    private static final Logger logger = LoggerFactory.getLogger(BuildMapperXml.class);

    private static final String BASE_COLUMN_LIST = "base_column_list";
    private static final String BASE_QUERY_CONDITION = "base_query_condition";
    private static final String BASE_QUERY_CONDITION_EXTEND = "base_query_condition_extend";
    private static final String QUERY_CONDITION = "query_condition";


    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_MAPPERS_XMLS);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String className = tableInfo.getBeanName() + Constants.SUFFIX_MAPPERS;

        File mapperFile = new File(folder, className + ".xml");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(mapperFile);
            outw = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(outw);

            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
            bw.write("<mapper namespace=\"" + Constants.PACKAGE_MAPPERS + "." + className + "\">\n");
            bw.newLine();

            bw.write("\t<!-- 实体映射-->\n");
            bw.write("\t<resultMap id=\"base_result_map\" type=\"" + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + "\">");
            bw.newLine();
            // 当主键只有一个的时候，获取主键，使用id映射；若主键多余一个，使用result映射
            String idKey = "";
            if (tableInfo.getKeyIndexMap().get("PRIMARY").size() == 1) {
                idKey = tableInfo.getKeyIndexMap().get("PRIMARY").get(0).getFieldName();
            }
            // 将fieldName与propertyName映射
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                String head = "";
                bw.write("\t\t<!-- " + fieldInfo.getComment() + "-->");
                bw.newLine();
                // 一个主键以id映射，tableInfo中的keyIndexMap映射了<键类型，fieldInfo>
                if ((fieldInfo.getFieldName()).equals(idKey)) {
                    head = "id";
                } else {
                    head = "result";
                }
                bw.write("\t\t<" + head + " column=\"" + fieldInfo.getFieldName() + "\" property=\"" + fieldInfo.getPropertyName() + "\" />");
                bw.newLine();
            }
            bw.write("\t</resultMap>\n");
            bw.newLine();

            bw.write("\t<!-- 通用查询结果列-->\n");
            bw.write("\t<sql id=\""+ BASE_COLUMN_LIST+ "\">");
            bw.newLine();
//            StringBuilder sb = new StringBuilder();
//            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
//                sb.append(fieldInfo.getFieldName()).append(", ");
//            }
//            String sb2 = sb.substring(0, sb.lastIndexOf(", "));
//            bw.write("\t\t" + sb2);
            bw.write("\t\t");
            Integer index = 0;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                index++;
                bw.write(fieldInfo.getFieldName());
                if (index < tableInfo.getFieldList().size()) {
                    bw.write(", ");
                }
            }
            bw.newLine();
            bw.write("\t</sql>\n");
            bw.newLine();

            bw.write("\t<!-- 基础查询条件-->\n");
            bw.write("\t<sql id=\""+ BASE_QUERY_CONDITION+ "\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                String stringQuery = "";
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())) {
                    stringQuery = " and query." + fieldInfo.getPropertyName() + " != ' '";
                }
                bw.write("\t\t<if test=\"query." + fieldInfo.getPropertyName() + " != null" + stringQuery+ "\">\n");
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())) {
                    bw.write("\t\t\t<![CDATA[ and " + fieldInfo.getFieldName() + " = str_to_date(#{query." + fieldInfo.getPropertyName() + "}, '%Y-%m-%d') ]]>\n");
                }else {
                    bw.write("\t\t\tand " + fieldInfo.getFieldName() + " = #{query." + fieldInfo.getPropertyName() + "}\n");
                }
                    bw.write("\t\t</if>\n");

            }
            bw.write("\t</sql>\n");
            bw.newLine();

            bw.write("\t<!-- 扩展查询条件-->\n");
            bw.write("\t<sql id=\""+ BASE_QUERY_CONDITION_EXTEND+ "\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getExtendFieldList()) {
                String andWhere = "";
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType())) {
                    andWhere = " and " + fieldInfo.getFieldName() + " like concat('%', #{query."+ fieldInfo.getPropertyName()+"}, '%')\n";
                }else if(ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())){
                    if(fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_START)){
                        andWhere = "<![CDATA[ and " + fieldInfo.getFieldName() + " >= str_to_date(#{query." + fieldInfo.getPropertyName() + "}, '%Y-%m-%d') ]]>\n";
                    }else if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_END)){
                        andWhere = "<![CDATA[ and " + fieldInfo.getFieldName() + " < date_sub(str_to_date(#{query." + fieldInfo.getPropertyName() + "}, '%Y-%m-%d'), interval -1 day) ]]>\n";
                    }
                }
                bw.write("\t\t<if test=\"query." + fieldInfo.getPropertyName() + " != null and query." + fieldInfo.getPropertyName() + " != ''\">\n");
                bw.write("\t\t\t" + andWhere);
                bw.write("\t\t</if>\n");
            }
            bw.write("\t</sql>\n");
            bw.newLine();

            bw.write("\t<!-- 通用查询条件(合并基础查询和扩展查询）-->\n");
            bw.write("\t<sql id=\""+ QUERY_CONDITION+ "\">\n");
            bw.write("\t\t<where>\n");
            bw.write("\t\t\t<include refid=\""+ BASE_QUERY_CONDITION+ "\"/>\n");
            bw.write("\t\t\t<include refid=\""+ BASE_QUERY_CONDITION_EXTEND+ "\"/>\n");
            bw.write("\t\t</where>\n");
            bw.write("\t</sql>\n");
            bw.newLine();


            bw.write("\t<!-- 查询列表-->\n");
            bw.write("\t<select id=\"selectList\" resultMap=\"base_result_map\">\n");
            bw.write("\t\tSELECT\n" +
                    "\t\t<include refid=\"" + BASE_COLUMN_LIST+ "\"/>\n" +
                    "\t\tFROM "+ tableInfo.getTableName() + "\n" +
                    "\t\t<include refid=\""+ QUERY_CONDITION+ "\"/>\n");
            bw.write("\t\t<if test=\"query.orderBy != null\">\n" +
                    "\t\t\torder by ${query.orderBy}\n" +
                    "\t\t</if>\n");
            bw.write("\t\t<if test=\"query.simplePage != null\">\n" +
                    "\t\t\tlimit #{query.simplePage.start}, #{query.simplePage.end}\n" +
                    "\t\t</if>\n");
            bw.write("\t</select>\n");
            bw.newLine();

            bw.write("\t<!-- 查询数量-->\n");
            bw.write("\t<select id=\"selectCount\" resultType=\"java.lang.Integer\">\n");
            bw.write("\t\tSELECT count(1) FROM "+ tableInfo.getTableName() + " <include refid=\"" + QUERY_CONDITION+ "\"/>\n");
            bw.write("\t</select>\n");
            bw.newLine();


            bw.write("\t<!-- 插入（匹配有值的字段）-->\n");
            bw.write("\t<insert id=\"insert\" parameterType=\"" + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + "\">\n");
            // 判断是否需要自增主键
            FieldInfo autoIncrementField = null;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.isAutoIncrement() != null && fieldInfo.isAutoIncrement()) {
                    autoIncrementField = fieldInfo;
                    break;
                }
            }
            if (autoIncrementField != null) {
                bw.write("\t\t<selectKey keyProperty=\"bean." + autoIncrementField.getFieldName() + "\" resultType=\"" + autoIncrementField.getJavaType() + "\" order=\"AFTER\">\n");
                bw.write("\t\t\tSELECT LAST_INSERT_ID()\n");
                bw.write("\t\t</selectKey>\n");
            }
            bw.write("\t\tINSERT INTO "+ tableInfo.getTableName() + "\n");

            bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">\n");
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ", \n");
                bw.write("\t\t\t</if>\n");
            }
            bw.write("\t\t</trim>\n");
            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\n");
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write( "\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">\n");
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "}, \n");
                bw.write("\t\t\t</if>\n");
            }
            bw.write("\t\t</trim>\n");
            bw.write("\t</insert>\n");
            bw.newLine();


            bw.write("\t<!-- 插入或更新（匹配有值的字段）-->\n");
            bw.write("\t<insert id=\"insertOrUpdate\" parameterType=\"" + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + "\">\n");
            bw.write("\t\tINSERT INTO "+ tableInfo.getTableName() + "\n");

            bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write( "\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">\n");
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ", \n");
                bw.write("\t\t\t</if>\n");
            }
            bw.write("\t\t</trim>\n");
            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\n");
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.write( "\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">\n");
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "}, \n");
                bw.write("\t\t\t</if>\n");
            }
            bw.write("\t\t</trim>\n");
            bw.write("\t\tON DUPLICATE KEY UPDATE\n");
            Map<String, String> keyTempMap = new HashMap<>();
            //去除索引，不能根据索引更新主键
            for(Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()){
                List<FieldInfo> fieldInfoList = entry.getValue();
                for(FieldInfo item : fieldInfoList){
                    keyTempMap.put(item.getFieldName(), item.getFieldName());
                }
            }
            bw.write("\t\t<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">\n");
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if(keyTempMap.get(fieldInfo.getFieldName()) != null){
                    continue;
                }
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">\n");
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + " = VALUES(" + fieldInfo.getFieldName() + "), \n");
                bw.write("\t\t\t</if>\n");
            }
            bw.write("\t\t</trim>\n");
            bw.write("\t</insert>\n");

            bw.write("\t<!-- 添加（批量插入）-->\n");
            bw.write("\t<insert id=\"insertBatch\" parameterType=\"" + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + "\">\n");
            bw.write("\t\tINSERT INTO "+ tableInfo.getTableName());
            bw.write("(");
            Integer index1 = 0;  // 与下面的StringBuffer同样是为了去掉最后一个逗号
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                index1++;
                if(fieldInfo.isAutoIncrement() != null && fieldInfo.isAutoIncrement()){
                    continue;
                }
                bw.write(fieldInfo.getFieldName());
                if(index1 < tableInfo.getFieldList().size()) {
                    bw.write(", ");
                }
            }
            bw.write(")values\n");
            bw.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">\n");
            StringBuffer insertPropertyBuffer = new StringBuffer();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if(fieldInfo.isAutoIncrement() != null && fieldInfo.isAutoIncrement()){
                    continue;
                }
                insertPropertyBuffer.append("#{item." + fieldInfo.getPropertyName() + "}").append(", ");
            }
            String insertPropertyStr = insertPropertyBuffer.substring(0, insertPropertyBuffer.lastIndexOf(", "));
            bw.write("\t\t\t(" + insertPropertyStr + ")\n");
            bw.write("\t\t</foreach>\n");
            bw.write("\t</insert>\n");

            bw.write("\t<!-- 批量新增修改（批量插入）-->\n");
            bw.write("\t<insert id=\"insertOrUpdateBatch\" parameterType=\"" + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + "\">\n");
            bw.write("\t\tINSERT INTO "+ tableInfo.getTableName());
            bw.write("(");
            Integer index2 = 0;  // 与下面的StringBuffer同样是为了去掉最后一个逗号
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                index2++;
                if(fieldInfo.isAutoIncrement() != null && fieldInfo.isAutoIncrement()){
                    continue;
                }
                bw.write(fieldInfo.getFieldName());
                if(index2 < tableInfo.getFieldList().size()) {
                    bw.write(", ");
                }
            }
            bw.write(")values\n");
            bw.write("\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">\n");
            bw.write("\t\t\t(" + insertPropertyStr + ")\n");
            bw.write("\t\t</foreach>\n");
            bw.write("\t\tON DUPLICATE KEY UPDATE\n");
            StringBuffer insertUpdateBatchBuffer = new StringBuffer();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if(fieldInfo.isAutoIncrement() != null && fieldInfo.isAutoIncrement()){
                    continue;
                }
                insertUpdateBatchBuffer.append("\t\t" + fieldInfo.getFieldName() + " = VALUES(" + fieldInfo.getFieldName() + "), \n");
            }
            String insertUpdateBatchStr = insertUpdateBatchBuffer.substring(0, insertUpdateBatchBuffer.lastIndexOf(", "));
            bw.write(insertUpdateBatchStr + "\n");
            bw.write("\t</insert>\n");





            Map<String, List<FieldInfo>> keyIndexMap = tableInfo.getKeyIndexMap();
            for(Map.Entry<String, List<FieldInfo>> entry : keyIndexMap.entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();

                Integer index0 = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuilder parameterName = new StringBuilder();

                for (FieldInfo fieldInfo : keyFieldInfoList) {
                    index0++;
                    methodName.append(StringUtiles.toUpperCaseFirstOne(fieldInfo.getPropertyName()));
                    parameterName.append(fieldInfo.getFieldName() + " = #{" + fieldInfo.getPropertyName() + "}");
                    if (index0 < keyFieldInfoList.size()) {
                        methodName.append("And");
                        parameterName.append(" and ");
                    }
                }

                bw.write("\t<!-- 根据" + methodName + "修改 -->\n");
                bw.write("\t<update id=\"updateBy" + methodName + "\" parameterType=\"" + Constants.PACKAGE_PO + "." + tableInfo.getBeanName() + "\">\n");
                bw.write("\t\tUPDATE "+ tableInfo.getTableName() + "\n");
                bw.write("\t\t<set>\n");
                FieldInfo idFieldInfo = null;
                for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                    if(fieldInfo.isAutoIncrement() != null && fieldInfo.isAutoIncrement()){
                        idFieldInfo = fieldInfo;
                        continue;
                    }
                    bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">\n");
                    bw.write("\t\t\t\t" + fieldInfo.getFieldName() + " = #{bean." + fieldInfo.getPropertyName() + "},\n");
                    bw.write("\t\t\t</if>\n");
                }
                bw.write("\t\t</set>\n");
                bw.write("\t\tWHERE " + parameterName + "\n");
                bw.write("\t</update>\n");


                bw.write("\t<!-- 根据" + methodName + "删除 -->\n");
                bw.write("\t<delete id=\"deleteBy" + methodName + "\">\n");
                bw.write("\t\tDELETE FROM " + tableInfo.getTableName() + " WHERE " + parameterName + "\n");
                bw.write("\t</delete>\n");

                bw.write("\t<!-- 根据PrimaryKey获取对象 -->\n");
                bw.write("\t<select id=\"selectBy" + methodName + "\" resultMap=\"base_result_map\">\n");
                bw.write("\t\tSELECT\n");
                bw.write("\t\t<include refid=\"" + BASE_COLUMN_LIST + "\"/>\n");
                bw.write("\t\tFROM " + tableInfo.getTableName() + " WHERE " + parameterName + "\n");
                bw.write("\t</select>\n");
            }

            bw.newLine();
            bw.write("</mapper>");
            bw.flush();
        } catch (Exception e) {
            logger.error("生成mapper.xml失败", e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outw != null) {
                try {
                    outw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
