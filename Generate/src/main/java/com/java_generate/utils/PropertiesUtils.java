package com.java_generate.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-01-22:42
 * @ Description：工具类--读配置文件
 */

/**
 * PropertiesUtils类用于从配置文件中加载属性
 * 它提供了一种通过键获取属性值的静态方法
 */
public class PropertiesUtils {
    // 使用Properties对象加载配置文件中的属性
    private static Properties props = new Properties();
    // 使用ConcurrentHashMap存储属性，以支持并发访问
    private static Map<String, String> PROPER_MAP = new ConcurrentHashMap<>();

    /**
     * 静态初始化块用于在类加载时从配置文件中加载属性
     */
    static {
        InputStream is = null;
        try {
            // 从类路径中获取配置文件"application.properties"的输入流
            is = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
            // 加载配置文件到Properties对象
            props.load(new InputStreamReader(is, "UTF-8"));

            // 遍历Properties对象的键集合，并将键值对放入ConcurrentHashMap中
            Iterator<Object> iterator = props.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = props.getProperty(key);
                PROPER_MAP.put(key, value);
            }
        } catch (Exception e) {
            // 异常情况已被捕获，但未处理，留空
        } finally {
            // 确保关闭输入流以释放资源
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据键获取属性值
     *
     * @param key 要查找的属性键
     * @return 属性值，如果未找到则返回null
     */
    public static String getString(String key) {
        return PROPER_MAP.get(key);
    }

    /**
     * 主方法用于测试getString方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        System.out.println(getString("db.driver.name"));
    }
}
