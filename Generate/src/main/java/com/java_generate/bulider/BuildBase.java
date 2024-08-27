package com.java_generate.bulider;

import com.java_generate.bean.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-05-16:56
 * @ Description：基础配置
 */

public class BuildBase {
    private static final Logger logger = LoggerFactory.getLogger(BuildBase.class);
    public static void execute() {
        List<String> headerInfoList = new ArrayList();
        // 构建时间类型枚举
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS);
        build(headerInfoList,"DateTimePatternEnum", Constants.PATH_ENUMS);
        headerInfoList.clear();
        // 构建时间生成工具
        headerInfoList.add("package " + Constants.PACKAGE_UTILS);
        build(headerInfoList,"DateUtils", Constants.PATH_UTILS);
        headerInfoList.clear();
        // 构建基础mapper
        headerInfoList.add("package " + Constants.PACKAGE_MAPPERS);
        build(headerInfoList,"BaseMapper", Constants.PATH_MAPPERS);
        headerInfoList.clear();
        // 生成pageSize枚举
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS);
        build(headerInfoList,"PageSize", Constants.PATH_ENUMS);
        headerInfoList.clear();
        // 生成分页
        headerInfoList.add("package " + Constants.PACKAGE_QUERY);
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".PageSize");
        build(headerInfoList,"SimplePage", Constants.PATH_QUERY);
        headerInfoList.clear();
        // 生成基础分页类
        headerInfoList.add("package " + Constants.PACKAGE_QUERY);
        build(headerInfoList,"BaseQuery", Constants.PATH_QUERY);
        headerInfoList.clear();
        // 生成paginationResultVO枚举
        headerInfoList.add("package " + Constants.PACKAGE_VO);
        build(headerInfoList,"PaginationResultVO", Constants.PATH_VO);
        headerInfoList.clear();
    }

    private static void build(List<String> headerInfoList, String fileName, String outPutPath) {
        File folder = new File(outPutPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File javaFile = new File(outPutPath, fileName + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        InputStream in = null;
        InputStreamReader inr = null;
        BufferedReader bf = null;
        try {
            out = new FileOutputStream(javaFile);
            outw = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(outw);

            String templatePath = BuildBase.class.getClassLoader().getResource("template/" + fileName + ".txt").getPath();
            in = new FileInputStream(templatePath);
            inr = new InputStreamReader(in, "UTF-8");
            bf = new BufferedReader(inr);
            // 导入包
            for (String headerInfo : headerInfoList) {
                if(headerInfo.contains("package")){
                    bw.write(headerInfo + ";");
                }
            }
            bw.newLine();
            for (String headerInfo : headerInfoList) {
                if(headerInfo.contains("import")){
                    bw.write(headerInfo + ";");
                }
            }
            bw.newLine();
            // 根据文件生成代码
            String line = null;
            while ((line = bf.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
        }catch (Exception e) {
            logger.error("生成基础类：{}", fileName, e);
        }finally {
            if (bf != null) {
                try {
                    bf.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inr != null) {
                try {
                    inr.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bw != null) {
                try {
                    bw.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outw != null) {
                try {
                    outw.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
