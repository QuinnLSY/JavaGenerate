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
 * @ Date：2024-08-27-12:03
 * @ Description：控制器
 */

public class BuildController {
    private static final Logger logger = LoggerFactory.getLogger(BuildController.class);

    public static void execute(TableInfo tableInfo){
        String className = tableInfo.getBeanName() + "Controller";
        String serviceClassName = tableInfo.getBeanName() + "Service";
        String serviceBeanName = StringUtiles.toLowerCaseFirstOne(serviceClassName);
        File folder = new File(Constants.PATH_CONTROLLER);
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
            bw.write("package " + Constants.PACKAGE_CONTROLLER + ";");
            bw.newLine();
            bw.newLine();
            // 导入包
            bw.write("import "+ Constants.PACKAGE_PO+"."+tableInfo.getBeanName()+";");
            bw.newLine();
            bw.write("import "+ Constants.PACKAGE_QUERY+"."+tableInfo.getBeanParamName()+";");
            bw.newLine();
            bw.write("import "+ Constants.PACKAGE_SERVICE+"."+ serviceClassName +";");
            bw.newLine();
            bw.write("import " + Constants.PACKAGE_VO + ".ResponseVO;");
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RequestBody;");
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RequestMapping;");
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RestController;");
            bw.newLine();
            bw.newLine();
            bw.newLine();
            bw.write("import javax.annotation.Resource;");
            bw.newLine();
            bw.write("import java.util.List;");
            bw.newLine();
            bw.newLine();


            BuildComment.creatClassComment(bw, tableInfo.getTableComment() + "控制器");
            bw.write("@RestController\n");
            bw.write("@RequestMapping(\"/"+ StringUtiles.toLowerCaseFirstOne(tableInfo.getBeanName())+"\")\n");
            bw.write("public class " + className + " extends ABaseController{\n\n");

            bw.write("\t@Resource\n");
            bw.write("\tprivate "+ serviceClassName + " " + serviceBeanName+";\n");

            BuildComment.creatFieldComment(bw, "加载数据列表");
            bw.write("\t@RequestMapping(\"/loadDataList\")\n");
            bw.write("\tpublic ResponseVO loadDataList(" + tableInfo.getBeanParamName() + " query){\n");
            bw.write("\t\treturn getSuccessResponseVO(" + serviceBeanName + ".findListByPage(query));\n");
            bw.write("\t}\n");

            BuildComment.creatFieldComment(bw, "新增");
            bw.write("\t@RequestMapping(\"/add\")\n");
            bw.write("\tpublic ResponseVO add(" + tableInfo.getBeanName() + " bean){\n");
            bw.write("\t\t" + serviceBeanName + ".add(bean);\n");
            bw.write("\t\treturn getSuccessResponseVO(null);\n");
            bw.write("\t}\n");

            BuildComment.creatFieldComment(bw, "批量新增");
            bw.write("\t@RequestMapping(\"/addBatch\")\n");
            bw.write("\tpublic ResponseVO addBatch(@RequestBody List<" + tableInfo.getBeanName() + "> listBean){\n");
            bw.write("\t\t" + serviceBeanName + ".addBatch(listBean);\n");
            bw.write("\t\treturn getSuccessResponseVO(null);\n");
            bw.write("\t}\n");

            BuildComment.creatFieldComment(bw, "批量新增或修改");
            bw.write("\t@RequestMapping(\"/addOrUpdateBatchBatch\")\n");
            bw.write("\tpublic ResponseVO addOrUpdateBatch(@RequestBody List<" + tableInfo.getBeanName() + "> listBean){\n");
            bw.write("\t\t" + serviceBeanName + ".addOrUpdateBatch(listBean);\n");
            bw.write("\t\treturn getSuccessResponseVO(null);\n");
            bw.write("\t}\n");

            for(Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();

                Integer index = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuilder methodParams = new StringBuilder();
                StringBuilder methodPropertyName = new StringBuilder();
                for(FieldInfo fieldInfo : keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtiles.toUpperCaseFirstOne(fieldInfo.getPropertyName()));
                    if(index < keyFieldInfoList.size()) {
                        methodName.append("And");
                    }
                    methodParams.append(fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName());
                    methodPropertyName.append(fieldInfo.getPropertyName());
                    if(index < keyFieldInfoList.size()) {
                        methodParams.append(", ");
                        methodPropertyName.append(", ");
                    }
                }
                BuildComment.creatFieldComment(bw, "根据" + methodName + "查询");
                String getMethod = "get" + tableInfo.getBeanName() + "By" + methodName;
                bw.write("\t@RequestMapping(\"/" + getMethod +"\")\n");
                bw.write("\tpublic ResponseVO " + getMethod + "(" + methodParams+ "){\n");
                bw.write("\t\treturn getSuccessResponseVO(" + serviceBeanName + "." +getMethod + "(" + methodPropertyName + "));\n");
                bw.write("\t}\n");
                bw.newLine();
                bw.newLine();

                BuildComment.creatFieldComment(bw, "根据" + methodName + "更新");
                String updateMethod = "update" + tableInfo.getBeanName() + "By" + methodName;
                bw.write("\t@RequestMapping(\"/" + updateMethod + "\")\n");
                bw.write("\tpublic ResponseVO "+ updateMethod + "(" + tableInfo.getBeanName() + " bean, " + methodParams+ "){\n");
                bw.write("\t\t" + serviceBeanName + "." + updateMethod + "(bean, " + methodPropertyName + ");\n");
                bw.write("\t\treturn getSuccessResponseVO(null);\n");
                bw.write("\t}\n");
                bw.newLine();
                bw.newLine();

                BuildComment.creatFieldComment(bw, "根据" + methodName + "删除");
                String deleteMethod = "delete" + tableInfo.getBeanName() + "By" + methodName;
                bw.write("\t@RequestMapping(\"/" + deleteMethod + "\")\n");
                bw.write("\tpublic ResponseVO "+deleteMethod + "(" + methodParams+ "){\n");
                bw.write("\t\t" + serviceBeanName + "." + deleteMethod + "(" + methodPropertyName + ");\n");
                bw.write("\t\treturn getSuccessResponseVO(null);\n");
                bw.write("\t}\n");
                bw.newLine();
                bw.newLine();

            }

            bw.write("}\n");

            bw.flush();
        }catch (Exception e){
            logger.error("生成Controller失败", e);
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
