package com.wy.code_generator.generator;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


import java.io.File;
import java.util.Collections;
import java.util.List;

import static com.wy.code_generator.generator.GeneratorConfig.*;
import static com.wy.code_generator.generator.GeneratorFileUtils.*;


/**
 * @author itw_zhangyy11
 * @create 2022/3/31 11:01
 * @Descrition:
 */
public class MyBatisPlusGeneratorCustom {

    /**
     * @description: 根据输入的表名生成相应的实体类，mapper, mapper.xml
     * @author itw_zhangyy11
     * @create 2022/3/28 9:14
     * @param table 表名
     * @param dataBaseType 数据库类型
     * @return : void
     */
    public static void generator(String table, String dataBaseType, String moudleName, String authorName) {
        // 实体类名
        String entityName = table + "Entity";
        // Mapper名
        String mapperName = table + "Dao";
        // Service 名
        String serviceName = table + "Service";
        // Service 实现类名
        String serviceImplName = table + "ServiceImpl";

        // 如果实体类存在就删掉 D:\project\bbc0309\bbc-newcontract-db-module\src\main\java\com\taikang\bbc\db\masterTest\entity\LCGrpContEntity.java
        String entityDir = getFileURL(PROJECT_DIR, JAVA_DIR, PARENT_PACKAGE_NAME, moudleName, ENTITY_DIR, entityName);
        File file = new File(entityDir + ".java");
        if (file.exists()) {
            file.delete();
        }

        FastAutoGenerator
                // 数据源配置
                .create(oracleDataSourceBuilder(dataBaseType))
                // 全局配置
                .globalConfig(builder -> {
                    // 设置作者名称
                    builder.author(authorName)
                            // 文件输出地址
                            .outputDir(OUTPUT_DIR)
                            //禁止打开输出目录
                            .disableOpenDir();
                })
                // 包配置
                .packageConfig(builder -> {
                    builder
                            // 父类包名
                            .parent(PARENT_PACKAGE_NAME)
                            // 模块名
                            .moduleName(moudleName)
                            // 实体类包名
                            .entity("entity")
                            // mapper包名
                            .mapper("mapper")
                            // mapper.xml 包名
                            .xml("mapping")
                            // mapper.xml地址 PROJECT_DIR + "\\" + RESOURCE_DIR + "\\" + moudleName + "\\" + MAPPER_XML_DIR
                            .pathInfo(Collections.singletonMap(OutputFile.xml, getFileURL(PROJECT_DIR, RESOURCE_DIR, moudleName, MAPPER_XML_DIR)));
                })
                // 模板配置
                .templateConfig(builder -> {
                    // 不生成Controller
                    builder.disable(TemplateType.CONTROLLER)
                            // Entity使用自定义模板
                            .entity("/templates/entity.java")
                            .mapper("/templates/mapper.java");
                })
                // 策略配置
                .strategyConfig(builder -> {
                    //数据库表名 LDCODEMID
                    builder.addInclude(table.toUpperCase())
                            // 实体类策略配置
                            .entityBuilder()
                            // 实体类启用 @Data注解
                            .enableLombok()
                            // 实体类名称
                            .formatFileName(entityName)

                            // service层配置
                            .serviceBuilder()
                            .formatServiceFileName(serviceName)
                            .formatServiceImplFileName(serviceImplName)

                            // mapper配置
                            .mapperBuilder()
                            // mapper名称
                            .formatMapperFileName(mapperName)
                            .superClass(BaseMapper.class)
                            // xml 名称
                            .formatXmlFileName(mapperName);
                })
                // 模板引擎
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

        // 使生成的Dao继承 对应的Mapper
        String daoFile = getFileURL(PROJECT_DIR, JAVA_DIR, PARENT_PACKAGE_NAME, moudleName, MAPPER_DIR, mapperName) + ".java";
        replaceStringLine(new File(daoFile), "REPLACE_BY_JAVA", table + "Mapper");
    }



    /**
     * @description: oracle 数据源配置
     * @author itw_zhangyy11
     * @create 2022/3/29 10:50
     * @param
     * @return : com.baomidou.mybatisplus.generator.config.DataSourceConfig.Builder
     */
    private static DataSourceConfig.Builder oracleDataSourceBuilder(String dataBaseType) {
        // 数据源配置
        String url = new String();
        String user = new String();
        String password = new String();
        switch (dataBaseType.toUpperCase()) {
            case "ORACLE":
                url = ORACLE_URL;
                user = ORACLE_USER;
                password = ORACLE_PASSWORD;
                break;
            case "MYSQL_BUSINESS":
                url = MYSQL_URL_BUSINESS;
                user = MYSQL_USER_BUSINESS;
                password = MYSQL_PASSWORD_BUSINESS;
                break;
            case "MYSQL_MIDDLEWARE":
                url = MYSQL_URL_MIDDLEWARE;
                user = MYSQL_USER_MIDDLEWARE;
                password = MYSQL_PASSWORD_MIDDLEWARE;
                break;
        }

        DataSourceConfig.Builder dataSourceBuilder = new DataSourceConfig
                // url, 用户名，密码
                .Builder(url, user, password)
                // 类型转换器
                .typeConvert(new OracleTypeConvert());
        return dataSourceBuilder;
    }


}