package com.java_generate.bulider;

import com.java_generate.bean.Constants;
import com.java_generate.bean.FieldInfo;
import com.java_generate.bean.TableInfo;
import com.java_generate.utils.DateUtils;
import com.java_generate.utils.StringUtiles;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-04-16:51
 * @ Description：productInfoQuery
 */

public class BuildQuery {
    private static final Logger logger = LoggerFactory.getLogger(BuildQuery.class);
    public static void execute(TableInfo tableInfo){
        File folder = new File(Constants.PATH_QUERY);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String className = tableInfo.getBeanName() + Constants.SUFFIX_BEAN_QUERY;

        File poFile = new File(folder, className + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(outw);

            // 写包名
            bw.write("package " + Constants.PACKAGE_QUERY + ";");
            bw.newLine();
            bw.newLine();
            if(tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }
            // 时间包
            if(tableInfo.getHaveDataTime() || tableInfo.getHaveDate()){
                bw.write("import java.util.Date;\n");
                bw.newLine();
            }

            bw.newLine();
            // 写类主体
            BuildComment.creatClassComment(bw, tableInfo.getTableComment() + "查询对象");
            bw.write("public class " + className +" extends BaseQuery{");
            bw.newLine();

             List<FieldInfo> extendList = new ArrayList<>();
            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                BuildComment.creatFieldComment(bw, fieldInfo.getComment());
                // 写主类
                bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                // 写模糊查询
                if(ArrayUtils.contains(Constants.SQL_STRING_TYPES, fieldInfo.getSqlType())){
                    String nameFuzzy = fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY;
                    BuildComment.creatFieldComment(bw, fieldInfo.getComment() + "（模糊查询）");
                    bw.write("\tprivate "+ fieldInfo.getJavaType() +" " + nameFuzzy + ";");
                    bw.newLine();

                    FieldInfo fuzzyFieldInfo = new FieldInfo();
                    fuzzyFieldInfo.setFieldName(fieldInfo.getFieldName());
                    fuzzyFieldInfo.setComment(fieldInfo.getComment());
                    fuzzyFieldInfo.setPropertyName(nameFuzzy);
                    fuzzyFieldInfo.setJavaType(fieldInfo.getJavaType());
                    fuzzyFieldInfo.setSqlType(fieldInfo.getSqlType());
                    extendList.add(fuzzyFieldInfo);
                }
                // 时间类型
                if(ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())){
                    String nameStart = fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_START;
                    String nameEnd = fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_END;
                    BuildComment.creatFieldComment(bw, fieldInfo.getComment() + "（开始时间）");
                    bw.write("\tprivate String " + nameStart + ";");
                    bw.newLine();
                    BuildComment.creatFieldComment(bw, fieldInfo.getComment() + "（结束时间）");
                    bw.write("\tprivate String " + nameEnd + ";");
                    bw.newLine();

                    FieldInfo startFieldInfo = new FieldInfo();
                    startFieldInfo.setFieldName(fieldInfo.getFieldName());
                    startFieldInfo.setComment(fieldInfo.getComment() + "（开始时间）");
                    startFieldInfo.setPropertyName(nameStart);
                    startFieldInfo.setJavaType("String");
                    startFieldInfo.setSqlType(fieldInfo.getSqlType());
                    extendList.add(startFieldInfo);

                    FieldInfo endFieldInfo = new FieldInfo();
                    endFieldInfo.setFieldName(fieldInfo.getFieldName());
                    endFieldInfo.setComment(fieldInfo.getComment() + "（结束时间）");
                    endFieldInfo.setPropertyName(nameEnd);
                    endFieldInfo.setJavaType("String");
                    endFieldInfo.setSqlType(fieldInfo.getSqlType());
                    extendList.add(endFieldInfo);
                }
            }
//            List<FieldInfo> queryFieldInfoList = tableInfo.getFieldList();
//            queryFieldInfoList.addAll(extendList);
            // 做一下list赋值，不要改动tableInfo的fieldList
            List<FieldInfo> queryFieldInfoList = new ArrayList<>();
            List<FieldInfo> fieldList = tableInfo.getFieldList();
            queryFieldInfoList.addAll(fieldList);
            queryFieldInfoList.addAll(extendList);
            //用于构造mapper.xml时的扩展字段列表
            tableInfo.setExtendFieldList(extendList);
//            for(FieldInfo eFieldInfo : queryFieldInfoList){
//                System.out.println("qu:" + eFieldInfo.getPropertyName());
//            }



            bw.newLine();
            bw.newLine();
            // getter、setter方法
            for(FieldInfo fieldInfo : queryFieldInfoList){
                bw.write("\tpublic void set" + StringUtiles.toUpperCaseFirstOne(fieldInfo.getPropertyName()) + "(" + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ") {");
                bw.newLine();
                bw.write("\t\tthis." + fieldInfo.getPropertyName() + " = " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                bw.write("\tpublic " + fieldInfo.getJavaType() + " get" + StringUtiles.toUpperCaseFirstOne(fieldInfo.getPropertyName()) + "() {");
                bw.newLine();
                bw.write("\t\treturn " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();
            }

            bw.write("}");
            bw.flush();
        }catch (Exception e){
            logger.error("生成Query失败", e);
        }finally {
            if(bw != null){
                try{
                    bw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (outw != null) {
                try {
                    outw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
