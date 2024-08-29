package com.java_generate_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-29-21:23
 * @ Description：启动
 */

@SpringBootApplication
@MapperScan(basePackages = "com.java_generate_demo.mappers")
public class RunDemoApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(RunDemoApplication.class, args);
    }
}
