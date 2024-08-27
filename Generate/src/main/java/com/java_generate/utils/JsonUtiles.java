package com.java_generate.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-03-22:06
 * @ Description：信息转json工具类
 */

public class JsonUtiles {
//    private static final Logger logger = LoggerFactory.getLogger(JsonUtiles.class);

    public static String convertObj2Json(Object obj){
        if(null == obj){
            return null;
        }
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }
}
