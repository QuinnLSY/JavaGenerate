package com.java_generate;

import com.java_generate.bean.Constants;
import com.java_generate.bean.TableInfo;
import com.java_generate.bulider.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-01-23:05
 * @ Description：启动类
 */

public class RunApplication {

    public static final Logger logger = LoggerFactory.getLogger(RunApplication.class);

    public static void main(String[] args) {
        logger.info("开始生成代码...");
        try {
            List<TableInfo> tableInfoList = BuildTable.getTables();
            BuildBase.execute();
            for (TableInfo tableInfo : tableInfoList) {
                System.out.println("开始生成表:" + tableInfo.getTableName());
                System.out.println("字段:" + tableInfo.getHaveBigDecimal());  // 在tableInfo中定义时要直接设为false，不能默认，否则会导致空指针
                BuildPo.execute(tableInfo);
                BuildQuery.execute(tableInfo);
                BuildMapper.execute(tableInfo);
                BuildMapperXml.execute(tableInfo);
                BuildService.execute(tableInfo);
                BuildServiceImpl.execute(tableInfo);
                BuildController.execute(tableInfo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("代码生成完毕...生成在目录:" + Constants.PATH_BASE);
    }
}
