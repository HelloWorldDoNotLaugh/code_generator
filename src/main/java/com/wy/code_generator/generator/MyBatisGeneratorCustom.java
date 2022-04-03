package com.wy.code_generator.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import static com.wy.code_generator.generator.GeneratorConfig.*;


/**
 * @author itw_zhangyy11
 * @create 2022/3/31 18:35
 * @Descrition:
 */
public class MyBatisGeneratorCustom {
    /**
     * @description: myBatis代码
     * @author itw_zhangyy11
     * @create 2022/3/31 14:10
     * @param dataBaseType 数据库类型
     * @param tableName 表名
     * @return : void
     */
    public static void generator(String tableName, String dataBaseType, String moduleName) throws Exception{
        // 执行过程中的警告信息
        ArrayList<String> warnings = new ArrayList<>();
        // 生成代码时覆盖原代码
        boolean overwrite = true;
        // 读取配置文件
        InputStream resourceAsStream = new FileInputStream(new File(MYBATIS_CONFIG_FILE));

        ConfigurationParser configurationParser = new ConfigurationParser(warnings);
        Configuration configuration = configurationParser.parseConfiguration(resourceAsStream);
        resourceAsStream.close();

        // 获取context
        Context context = configuration.getContext("default");

        //
        getTableConfiguration(context, tableName, dataBaseType);

        // 配置数据库连接
        JDBCConnectionConfiguration jdbcConfig = getJdbcConfig(context, dataBaseType);
        context.setJdbcConnectionConfiguration(jdbcConfig);

        // javaModelGenerator
        JavaModelGeneratorConfiguration javaModelGenerator = getJavaModelGenerator(context, moduleName);
        context.setJavaModelGeneratorConfiguration(javaModelGenerator);

        JavaClientGeneratorConfiguration javaClientConfig = getJavaClientConfig(context, moduleName);
        context.setJavaClientGeneratorConfiguration(javaClientConfig);

        // sqlMapGenerator
        SqlMapGeneratorConfiguration sqlMapConfig = getSqlMapConfig(context, moduleName);
        context.setSqlMapGeneratorConfiguration(sqlMapConfig);



        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }

    }

    private static List<TableConfiguration> getTableConfiguration(Context context, String tableName, String dataBaseType) {
        List<TableConfiguration> tableConfigurations = context.getTableConfigurations();
        for (TableConfiguration tableConfiguration : tableConfigurations) {
            String user = "";
            switch (dataBaseType) {
                case DATABASE_ORACLE:
                    user = ORACLE_USER;
                    break;
                case DATABASE_MYSQL_BUSINESS:
                    user = MYSQL_USER_BUSINESS;
                    break;
                case DATABASE_MYSQL_MIDDLEWARE:
                    user = MYSQL_USER_MIDDLEWARE;
                    break;
            }
//            tableConfiguration.setSchema(user);
            tableConfiguration.setTableName(tableName);
            tableConfiguration.setMapperName(tableName + "Mapper");
            tableConfiguration.setDomainObjectName(tableName + "Entity");
            tableConfiguration.setCountByExampleStatementEnabled(false);
            tableConfiguration.setDeleteByExampleStatementEnabled(false);
            tableConfiguration.setSelectByExampleStatementEnabled(false);
            tableConfiguration.setUpdateByExampleStatementEnabled(false);
            tableConfiguration.setSelectByExampleStatementEnabled(false);
        }
        return tableConfigurations;
    }

    /**
     * @description: myBatis代码生成器JDBC配置
     * @author itw_zhangyy11
     * @create 2022/3/31 10:37
     * @param  dataBaseType 数据库类型
     * @return : org.mybatis.generator.config.JDBCConnectionConfiguration
     */
    private static JDBCConnectionConfiguration getJdbcConfig(Context context, String dataBaseType) {
        JDBCConnectionConfiguration configuration = context.getJdbcConnectionConfiguration();
        switch (dataBaseType.toLowerCase()) {
            case DATABASE_ORACLE :
                configuration.setDriverClass(ORACLE_DRIVER);
                configuration.setConnectionURL(ORACLE_URL);
                configuration.setUserId(ORACLE_USER);
                configuration.setPassword(ORACLE_PASSWORD);
                break;
            case DATABASE_MYSQL_BUSINESS :
                configuration.setDriverClass(MYSQL_DRIVER);
                configuration.setConnectionURL(MYSQL_URL_BUSINESS);
                configuration.setUserId(MYSQL_USER_BUSINESS);
                configuration.setPassword(MYSQL_PASSWORD_BUSINESS);
                break;
            case DATABASE_MYSQL_MIDDLEWARE :
                configuration.setDriverClass(MYSQL_DRIVER);
                configuration.setConnectionURL(MYSQL_URL_MIDDLEWARE);
                configuration.setUserId(MYSQL_USER_MIDDLEWARE);
                configuration.setPassword(MYSQL_PASSWORD_MIDDLEWARE);
                break;
        }
        return configuration;
    }


    private static JavaModelGeneratorConfiguration getJavaModelGenerator(Context context, String moduleName) {
        JavaModelGeneratorConfiguration javaModelConfig = context.getJavaModelGeneratorConfiguration();
        javaModelConfig.setTargetProject(JAVA_DIR);
        javaModelConfig.setTargetPackage(PARENT_PACKAGE_NAME + "." + moduleName + "." + ENTITY_DIR);
        return javaModelConfig;
    }

    private static JavaClientGeneratorConfiguration getJavaClientConfig(Context context, String moduleName) {
        JavaClientGeneratorConfiguration javaClientConfig = context.getJavaClientGeneratorConfiguration();
        javaClientConfig.setTargetProject(JAVA_DIR);
        javaClientConfig.setTargetPackage(PARENT_PACKAGE_NAME + "." + moduleName + "." + MAPPER_DIR);
        return javaClientConfig;
    }

    private static SqlMapGeneratorConfiguration getSqlMapConfig(Context context, String moduleName) {
        SqlMapGeneratorConfiguration sqlMapConfig = context.getSqlMapGeneratorConfiguration();
        sqlMapConfig.setTargetProject(RESOURCE_DIR);
        sqlMapConfig.setTargetPackage(moduleName + "." + MAPPER_XML_DIR);
        return sqlMapConfig;
    }


}