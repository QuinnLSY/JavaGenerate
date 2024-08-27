package com.java_generate.bulider;

import com.java_generate.bean.Constants;

import java.io.BufferedWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @ Tool：IntelliJ IDEA
 * @ Author：单纯同学
 * @ Date：2024-08-04-21:40
 * @ Description：构建注释
 */

public class BuildComment {
    public static void creatClassComment(BufferedWriter bw, String classComment) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        String currentDate = sdf.format(new Date());

        bw.write("/**\n" +
                " * @ Author: " + Constants.AUTHOR_COMMENT + "\n" +
                " * @ Date: " + currentDate + "\n" +
                " * @ Description: " + classComment + "\n" +
                " */\n");
    }

    public static void creatFieldComment(BufferedWriter bw, String fieldComment) throws Exception {
        if(fieldComment != null) {
            bw.write("\t/**\n" +
                    "\t * " + fieldComment + "\n" +
                    "\t */\n");
        }
    }

    public static void creatMethodComment() throws Exception {

    }
}
