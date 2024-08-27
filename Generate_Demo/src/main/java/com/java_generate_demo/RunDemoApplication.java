package com.java_generate_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
// Spring Boot 应用程序入口类
// 通过 @SpringBootApplication 注解开启 Spring Boot 的所有功能
// @MapperScan 注解用于扫描并自动装配 MyBatis 的 Mapper 接口
@SpringBootApplication
@MapperScan(basePackages = "com.java_generate_demo.mappers")
public class RunDemoApplication {
    // 应用程序的主入口方法
    // @param args 命令行参数，用于传递应用程序启动参数
    public static void main( String[] args ) {
        // 启动 Spring Boot 应用程序
        SpringApplication.run(RunDemoApplication.class, args);
    }
}
