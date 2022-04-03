package com.wy.code_generator.generator;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.List;

/**
 * @author HelloWorld
 * @create 2022/4/3 17:24
 * @email helloworld.dng@gmail.com
 */
public class GeneratorFileUtils {
    public static void main(String[] args) {
        //PROJECT_DIR + "\\" + JAVA_DIR + "\\" + PARENT_PACKAGE_NAME + "\\" + moudleName + "\\" + ENTITY_DIR + "\\" + entityName;
    }

    /**
     * @description: 逐行将 file 中的 oldStr 替换为 newStr
     * @author itw_zhangyy11
     * @create 2022/4/1 13:30
     * @param file  文件地址
     * @param oldStr 需要替换的字符串
     * @param newStr
     * @return : void
     */
    public static void replaceStringLine(File file, String oldStr, String newStr) {
        // 逐行读取文件
        List<String> strings = FileUtil.readLines(file, "UTF-8");
        for (int i = 0; i < strings.size(); i++) {
            String temp = strings.get(i).replaceAll(oldStr, newStr);
            strings.remove(i);
            strings.add(i, temp);
        }
        FileUtil.writeLines(strings, file, "UTF-8");
    }

    /**
     * @description: 将传进来的路径拼接 生成其对应的java 文件地址
     *     例如传入 D:\project\bbc0309test, bbc-newcontract-db-module\src\main\java, com.taikang.bbc.db, master, entity, LJSPayEntity
     *     返回  D:\project\bbc0309test\bbc-newcontract-db-module\src\main\java\com\taikang\bbc\db\mast\entity\LJSPayEntity
     * @author itw_zhangyy11
     * @create 2022/4/1 13:52
     * @param list
     * @return : java.lang.String
     */
    public static String getFileURL(String ...list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            s  = s.replace(".", "\\");
            stringBuilder.append(s);
            stringBuilder.append("\\");
        }
        // 删除最后一个 \ 并返回
        return stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "").toString();

    }
}
