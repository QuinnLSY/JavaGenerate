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


/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-04-16:51
 * @ Description：productInfo
 */

public class BuildPo {
    private static final Logger logger = LoggerFactory.getLogger(BuildPo.class);
    public static void execute(TableInfo tableInfo){
        File folder = new File(Constants.PATH_PO);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File poFile = new File(folder, tableInfo.getBeanName() + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(outw);

            // 写包名
            bw.write("package " + Constants.PACKAGE_PO + ";");
            bw.newLine();
            bw.newLine();
            bw.write("import java.io.Serializable;");
            bw.newLine();
            if(tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }
            // 时间包
            if(tableInfo.getHaveDataTime() || tableInfo.getHaveDate()){
                bw.write("import java.util.Date;\n");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_FORMAT_CLASS + ";");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_PARSE_CLASS + ";");
                bw.newLine();
                bw.write("import "+ Constants.PACKAGE_UTILS+".DateUtils;");
                bw.newLine();
                bw.write("import "+ Constants.PACKAGE_ENUMS+".DateTimePatternEnum;");
                bw.newLine();
            }
            // 属性忽略包
            for(FieldInfo fieldInfo : tableInfo.getFieldList()){
                if(ArrayUtils.contains(Constants.IGNORE_BEAN_TOJSON_FIELD.split(","), fieldInfo.getPropertyName())){
                    bw.write(Constants.IGNORE_BEAN_TOJSON_CLASS + ";");
                    bw.newLine();
                    break;
                }
            }

            bw.newLine();
            // 写类主体
            BuildComment.creatClassComment(bw, tableInfo.getTableComment());
            bw.write("public class " + tableInfo.getBeanName() + " implements Serializable {");
            bw.newLine();

            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                BuildComment.creatFieldComment(bw, fieldInfo.getComment());
                // 添加属性注解
                if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())){
                    bw.write("\t" + String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION, DateUtils.YY_MM_DD_HH_MM_SS));
                    bw.newLine();
                    bw.write("\t" + String.format(Constants.BEAN_DATE_PARSE_EXPRESSION, DateUtils.YY_MM_DD_HH_MM_SS));
                    bw.newLine();
                }
                if(ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())){
                    bw.write("\t" + String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION, DateUtils.YY_MM_DD));
                    bw.newLine();
                    bw.write("\t" + String.format(Constants.BEAN_DATE_PARSE_EXPRESSION, DateUtils.YY_MM_DD));
                    bw.newLine();
                }
                if(ArrayUtils.contains(Constants.IGNORE_BEAN_TOJSON_FIELD.split(","), fieldInfo.getPropertyName())){
                    bw.write("\t" + String.format(Constants.IGNORE_BEAN_TOJSON_EXPRESSION));
                    bw.newLine();
                }
                // 写主类
                bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
            }
            bw.newLine();
            bw.newLine();
            // getter、setter方法
            for(FieldInfo fieldInfo : tableInfo.getFieldList()){
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

            // 重写toString方法
            StringBuffer toString = new StringBuffer();
            for(FieldInfo fieldInfo : tableInfo.getFieldList()){
                String properName = fieldInfo.getPropertyName();
                if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES, fieldInfo.getSqlType())){
                    properName = "DateUtils.format(" + properName + ", DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())";
                }else if(ArrayUtils.contains(Constants.SQL_DATE_TYPES, fieldInfo.getSqlType())){
                    properName = "DateUtils.format(" + properName + ", DateTimePatternEnum.YYYY_MM_DD.getPattern())";
                }
                toString.append(fieldInfo.getComment() + ":\" + (" + fieldInfo.getPropertyName() + " == null ? \"空\" : " + properName + ") ");
                toString.append("+ \", ");
            }
            String toStringStr = toString.toString();
            toStringStr = "\"" + toStringStr;
            String toSubStringStr = toStringStr.substring(0, toStringStr.lastIndexOf("+"));
            bw.write("\t@Override");
            bw.newLine();
            bw.write("\tpublic String toString() {");
            bw.newLine();
            bw.write("\t\treturn " + toSubStringStr + ";\n");
            bw.write("\t}\n");

            bw.write("}");
            bw.flush();
        }catch (Exception e){
            logger.error("生成PO失败", e);
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
