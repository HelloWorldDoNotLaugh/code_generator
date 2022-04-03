package com.wy.code_generator.generator;

import java.util.HashMap;
import java.util.Scanner;
import static com.wy.code_generator.generator.GeneratorConfig.*;

/**
 * @author HelloWorld
 * @create 2022/4/3 17:17
 * @email helloworld.dng@gmail.com
 */
public class CodeGenerator {
    public static void main(String[] args) throws Exception{
        // 输入新建类作者名称
        System.out.println(">>>>>>>>>>请输入新建类的作者名称 【OA账号】");
        Scanner scanner = new Scanner(System.in);
        String authorName = scanner.next();
        // 选择数据库类型
        String dataBaseType = chooseDataBaseType();
        // 输入表名
        String[] tableNameArray = CodeGenerator.inputTableName();
        for (String tableName : tableNameArray) {
            if ("".equals(tableName.trim())) {
                continue;
            }
            String moudleName = getMoudleName(dataBaseType);
            // 调用MyBatis代码生成器
            MyBatisGeneratorCustom.generator(tableName, dataBaseType, moudleName);
            // 调用MyBatisPlus代码生成器
            MyBatisPlusGeneratorCustom.generator(tableName, dataBaseType, moudleName, authorName);
        }
    }

    /**
     * @description: 选择数据源
     * @author itw_zhangyy11
     * @create 2022/3/31 13:37
     * @param
     * @return : String  1 -> oracle  2 -> mysql_business  3 -> mysql_middleware
     */
    private static String chooseDataBaseType() {
        HashMap<Integer, String> dataBaseTypeMap = new HashMap<>(3);
        dataBaseTypeMap.put(1, DATABASE_ORACLE);
        dataBaseTypeMap.put(2, DATABASE_MYSQL_BUSINESS);
        dataBaseTypeMap.put(3, DATABASE_MYSQL_MIDDLEWARE);
        System.out.println(">>>>>>>>>>请选择数据源 【1】oracle  【2】mysql_business 【3】mysql_middleware");
        int dataBaseTypeChoose = 0;
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                dataBaseTypeChoose = scanner.nextInt();
                if(dataBaseTypeMap.containsKey(dataBaseTypeChoose)) {
                    return dataBaseTypeMap.get(dataBaseTypeChoose);
                }else {
                    System.out.println("【ERROR!】只能输入1、2、3");
                }
            } catch (Exception e) {
                System.out.println("【ERROR!】只能输入1、2、3");
            }
        }
    }

    /**
     * @description: 输入表名
     * @author itw_zhangyy11
     * @create 2022/3/31 13:58
     * @param
     * @return : java.lang.String[] 表名数组
     */
    private static String[] inputTableName() {
        System.out.println(">>>>>>>>>>请输入表名");
        System.out.println("【注意！！！】");
        System.out.println("    1. 表名使用大驼峰命名");
        System.out.println("    2. 多个表名之间使用空格隔开");
        System.out.println("_____________________________________");
        Scanner scanner = new Scanner(System.in);
        String next = scanner.nextLine();
        String[] tableName = next.split(" ");

        return tableName;
    }

    /** 获取不同数据库类型对应的模块名称
     * @description:
     * @author itw_zhangyy11
     * @create 2022/4/1 11:27
     * @param dataBaseType 数据库类型
     * @return : java.lang.String 模块名称  oracle -> master  mysql_Business -> mysql_business  mysql_middleware -> mysql_middleware
     */
    private static String getMoudleName(String dataBaseType) {
        switch (dataBaseType) {
            case DATABASE_ORACLE:
                return MODULE_NAME_MASTER;
            case DATABASE_MYSQL_BUSINESS:
                return MOUDLE_NAME_MYSQL_BUSINESS;
            case DATABASE_MYSQL_MIDDLEWARE:
                return MOUDLE_NAME_MYSQL_MIDDLEWARE;
            default:  return null;
        }
    }

}
