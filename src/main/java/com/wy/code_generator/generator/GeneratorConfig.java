package com.wy.code_generator.generator;

/**
 * @author HelloWorld
 * @create 2022/4/3 17:23
 * @email helloworld.dng@gmail.com
 */
public class GeneratorConfig {

    /* ************************************↓↓↓ 数据源配置 ↓↓↓*************************************/

    /** oracle 数据源配置 */
    /** 数据库类型 */
    public static final String DATABASE_ORACLE = "oracle";
    /** 数据库驱动 */
    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    /** url */
    public static final String ORACLE_URL = "jdbc:oracle:asda";
    /** 用户名 */
    public static final String ORACLE_USER = "dsa";
    /** 密码 */
    public static final String ORACLE_PASSWORD = "asdad";

    /** mysql数据源配置 */
    /** 数据库驱动 */
    public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    /** 数据库类型 */
    public static final String DATABASE_MYSQL_BUSINESS = "mysql_business";
    /** url */
    public static final String MYSQL_URL_BUSINESS = "jdbc:mysql://127.0.0.1:3306/NiuKe?charactorEncoding=utf8&useSSL=false&serverTimeZone=Asia/Shanghai&allowMultiQueries=true";
    /** 用户名 */
    public static final String MYSQL_USER_BUSINESS = "root";
    /** 密码 */
    public static final String MYSQL_PASSWORD_BUSINESS = "root";

    /** mysql数据源配置 */
    /** 数据库类型 */
    public static final String DATABASE_MYSQL_MIDDLEWARE = "mysql_middleware";
    /** url */
    public static final String MYSQL_URL_MIDDLEWARE = "dsaadimeZone=Asia/Shanghai&allowMultiQueries=true";
    /** 用户名 */
    public static final String MYSQL_USER_MIDDLEWARE = "dsadas";
    /** 密码 */
    public static final String MYSQL_PASSWORD_MIDDLEWARE = "dasada";


    /* ************************************↓↓↓ 相关地址配置 ↓↓↓*************************************/

    /** 项目地址 */
    public static final String PROJECT_DIR = System.getProperty("user.dir");
    /** myBatis配置文件地址 */
    public static final String MYBATIS_CONFIG_FILE = PROJECT_DIR  + "\\src\\main\\resources\\mybatis-generator.xml";
    /** java层文件地址 */
    public static final String JAVA_DIR = "bbc-newcontract-db-module\\src\\main\\java";
    /** resource层地址 */
    public static final String RESOURCE_DIR = "bbc-newcontract-db-module\\src\\main\\resources";
    /** mybatisPlus文件输出地址 */
    public static final String OUTPUT_DIR = PROJECT_DIR + "\\" + JAVA_DIR;






    /* ************************************↓↓↓ 名称设置 ↓↓↓*************************************/
    /** 包名 */
    public static final String PARENT_PACKAGE_NAME = "com.taikang.bbc.db";
    /** 模块名 */
    public static final String MODULE_NAME_MASTER = "master";
    public static final String MOUDLE_NAME_MYSQL_BUSINESS = "mysql_business";
    public static final String MOUDLE_NAME_MYSQL_MIDDLEWARE = "mysql_middleware";
    /** xml所在包名*/
    public static final String MAPPER_XML_DIR = "mapping";
    /** mapper所在包名 */
    public static final String MAPPER_DIR = "mapper";
    /** entity所在包名 */
    public static final String ENTITY_DIR = "entity";
    /** 作者名称 */
    public static final String AUTHOR_NAME = "itw_zhangyy11";

}
