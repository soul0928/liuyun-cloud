package com.liuyun.core.mybatisplus;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

public class Generator {

    public static void main(String[] args) {
        autogenerate();
    }

    private static void autogenerate(){
        String moduleName = "liuyun";
        String []tables = {
                            "sys_user",
        };
        generatorTables(moduleName, tables);
    }

    public static void generatorTables(String moduleName, String... tableName) {
        //获取基础配置
        ResourceBundle rb = ResourceBundle.getBundle("mybatis-plus");
        String dbUrl = rb.getString("datasource.url");
        String dbUsername = rb.getString("datasource.username");
        String dbPassword = rb.getString("datasource.password");
        String dbDriver = rb.getString("datasource.driver-class-name");
        String author = rb.getString("author");
        String parent = rb.getString("parent");

        boolean restController = Boolean.parseBoolean(rb.getString("rest-controller"));
        boolean lombok = Boolean.parseBoolean(rb.getString("lombok"));
        String superEntityClass = rb.getString("super-entity-class");
        String superControllerClass = rb.getString("super-controller-class");
        String superServiceClass = rb.getString("super-service-class");
        String superServiceImplClass = rb.getString("super-service-implclass");
        String superMapperClass = rb.getString("super-mapper-class");

        String projectPath = rb.getString("output-dir");

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/java");
        gc.setAuthor(author);
        gc.setFileOverride(false);
        gc.setActiveRecord(true);
        gc.setEnableCache(false); //XML 二级缓存设置
        gc.setBaseResultMap(false);
        gc.setBaseColumnList(false);
        gc.setOpen(false);
        gc.setSwagger2(true);

        //设置生成的文件名称
        gc.setMapperName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        gc.setEntityName("%sEntity");


        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(dbUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName(dbDriver);
        dsc.setUsername(dbUsername);
        dsc.setPassword(dbPassword);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(parent);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("basepackage", parent + '.' + moduleName);
                this.setMap(map);
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        //dto
        /*focList.add(new FileOutConfig("/templates/dto.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Mapper文件生成路径
                return projectPath + "/java/com/liuyun/model/" + pc.getModuleName()
                        + "/dto/" + tableInfo.getEntityName().replace("Entity", "") + "DTO.java";
            }
        });*/

        focList.add(new FileOutConfig("/templates/cmapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName().replace("Entity", "") + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        TemplateConfig tc = new TemplateConfig();

        tc.setXml(null);
        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        if (StrUtil.isNotEmpty(superEntityClass)) {
            strategy.setSuperEntityClass(superEntityClass);
            strategy.setEntityLombokModel(true);
        }
        strategy.setRestControllerStyle(restController);
        if (StrUtil.isNotEmpty(superControllerClass)) {
            strategy.setSuperControllerClass(superControllerClass);
        }

        if (StrUtil.isNotEmpty(superServiceClass)) {
            strategy.setSuperServiceClass(superServiceClass);
        }
        if(StrUtil.isNotEmpty(superServiceImplClass)){
            strategy.setSuperServiceImplClass(superServiceImplClass);
        }
        if (StrUtil.isNotEmpty(superMapperClass)) {
            strategy.setSuperMapperClass(superMapperClass);
        }

        strategy.setInclude(tableName);

        strategy.setSuperEntityColumns("id",
                "create_user_id",
                "create_time",
                "update_user_id",
                "update_time",
                "remark",
                "version",
                "del_flag");

        strategy.setControllerMappingHyphenStyle(true);
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setEntityLombokModel(lombok);
        strategy.setEntityTableFieldAnnotationEnable(true);

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
