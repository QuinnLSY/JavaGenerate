package com.java_generate.utils;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-03-15:45
 * @ Description：将一个字母转换为首字母大写的工具类
 */

public class StringUtiles {
    // 将一个字母转换为首字母大写
    public static String toUpperCaseFirstOne(String filed) {
        if (Character.isUpperCase(filed.charAt(0)))
            return filed;
        else
            return (new StringBuilder()).append(Character.toUpperCase(filed.charAt(0))).append(filed.substring(1)).toString();
    }

    // 将一个字母转换为首字母小写
    public static String toLowerCaseFirstOne(String filed) {
        if (Character.isLowerCase(filed.charAt(0)))
            return filed;
        else
            return (new StringBuilder()).append(Character.toLowerCase(filed.charAt(0))).append(filed.substring(1)).toString();
    }

    public static void main(String[] args) {
        System.out.println(toUpperCaseFirstOne("id"));
    }
}
