package com.java_generate.bulider;

import com.java_generate.bean.Constants;
import com.java_generate.bean.FieldInfo;
import com.java_generate.bean.TableInfo;
import com.java_generate.utils.StringUtiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-27-09:58
 * @ Description：服务类
 */

public class BuildService {
    private static final Logger logger = LoggerFactory.getLogger(BuildService.class);

    public static void execute(TableInfo tableInfo){
        String className = tableInfo.getBeanName() + "Service";
        File folder = new File(Constants.PATH_SERVICE);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File serviceFile = new File(folder, className + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(serviceFile);
            outw = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(outw);

            // 写包名
            bw.write("package " + Constants.PACKAGE_SERVICE + ";");
            bw.newLine();
            bw.newLine();
            // 导入包
            bw.write("import "+ Constants.PACKAGE_PO+"."+tableInfo.getBeanName()+";");
            bw.newLine();
            bw.write("import "+ Constants.PACKAGE_QUERY+"."+tableInfo.getBeanParamName()+";");
            bw.newLine();
            bw.write("import "+ Constants.PACKAGE_VO+".PaginationResultVO;");
            bw.newLine();
            bw.newLine();
            bw.write("import java.util.List;");
            bw.newLine();
            bw.newLine();


            BuildComment.creatClassComment(bw, tableInfo.getTableComment() + "服务类");
            bw.write("public interface " + className + " {\n");

            BuildComment.creatFieldComment(bw, "根据条件查询列表");
            bw.write("\tList<" + tableInfo.getBeanName() + "> findListByParam(" + tableInfo.getBeanParamName() + " query);\n");
            BuildComment.creatFieldComment(bw, "根据条件查询数量");
            bw.write("\tInteger findCountByParam(" + tableInfo.getBeanParamName() + " query);\n");
            BuildComment.creatFieldComment(bw, "分页查询");
            bw.write("\tPaginationResultVO<" + tableInfo.getBeanName() + "> findListByPage(" + tableInfo.getBeanParamName() + " query);\n");
            BuildComment.creatFieldComment(bw, "新增");
            bw.write("\tInteger add(" + tableInfo.getBeanName() + " bean);\n");
            BuildComment.creatFieldComment(bw, "批量新增");
            bw.write("\tInteger addBatch(List<" + tableInfo.getBeanName() + "> listBean);\n");
            BuildComment.creatFieldComment(bw, "批量新增或修改");
            bw.write("\tInteger addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean);\n");

            for(Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();

                Integer index = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuilder methodParams = new StringBuilder();
                for(FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtiles.toUpperCaseFirstOne(fieldInfo.getPropertyName()));
                    if(index < keyFieldInfoList.size()) {
                        methodName.append("And");
                    }
                    methodParams.append(fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    if(index < keyFieldInfoList.size()) {
                        methodParams.append(", ");
                    }
                }
                BuildComment.creatFieldComment(bw, "根据" + methodName + "查询");
                bw.write("\t" + tableInfo.getBeanName() + " get"+tableInfo.getBeanName()+"By" + methodName + "(" + methodParams+ ");");
                bw.newLine();
                bw.newLine();

                BuildComment.creatFieldComment(bw, "根据" + methodName + "更新");
                bw.write("\tInteger update"+tableInfo.getBeanName()+"By" + methodName + "(" + tableInfo.getBeanName() + " bean, " + methodParams+ ");");
                bw.newLine();
                bw.newLine();

                BuildComment.creatFieldComment(bw, "根据" + methodName + "删除");
                bw.write("\tInteger delete"+tableInfo.getBeanName()+"By" + methodName + "(" + methodParams+ ");");
                bw.newLine();
                bw.newLine();

            }

            bw.write("}\n");


            
            bw.flush();
        }catch (Exception e){
            logger.error("生成Service失败", e);
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
