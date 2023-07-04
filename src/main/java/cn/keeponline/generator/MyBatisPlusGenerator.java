package cn.keeponline.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.querys.DMQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.*;

public class MyBatisPlusGenerator {

    //作者名
    private static final String AUTHOR = "xincai peng";
    //功能模块名称，生成的文件会存放到模块下
    private static final String MODULE_NAME = "test";
    //包路径
    private static final String BASE_PACKAGE = "com.mybatis.demo";
    //要生成的表名
    private static final String[] TABLES = {"DIRECT_OTHER","DIRECT_EXTEND","DIRECT_BASE"};
    private static final String JDBC_URL = "jdbc:dm://10.26.28.25:5236/XRDEV_MEASURE";
    private static final String JDBC_USERNAME = "XRDEV_MEASURE";
    private static final String JDBC_PASSWORD = "XRDEV_MEASURE";

    public static void main(String[] args) {
        //当前项目路径
        String projectPath = "F:\\generator";

        //数据库配置
        DataSourceConfig dataSourceConfig = configDataSource();
        // 代码生成器
        AutoGenerator generator = new AutoGenerator(dataSourceConfig);

        //全局配置
        configGlobal(generator, projectPath);
        //包相关配置
        configPackage(generator);
        //策略配置
        configStrategy(generator);
        //自定义配置
        configCustom(generator, projectPath);
        //模版引擎配置
        configTemplate(generator);

        generator.execute(new VelocityTemplateEngine());
    }

    /**
     * 进行数据库相关配置
     *
     */
    private static DataSourceConfig configDataSource() {
        //数据源配置
        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        dataSourceConfig.dbQuery(new DMQuery());
        // dataSourceConfig.schema("public");
        return dataSourceConfig.build();
    }

    /**
     * 进行全局配置
     *
     * @param generator   :
     * @param projectPath :
     */
    private static void configGlobal(AutoGenerator generator, String projectPath) {
        // 全局配置
        GlobalConfig.Builder globalConfig = new GlobalConfig.Builder();
        //生成文件输出存放路径 = 当前项目路径 + 想存放到项目中的路径
        String fileOutputPatch = projectPath.concat("/src");
        globalConfig.outputDir(fileOutputPatch);

        //设置作者
        globalConfig.author(AUTHOR);
        globalConfig.disableOpenDir();
        //是否开启 swagger2 模式,实体属性 Swagger2 注解,默认false
        //globalConfig.enableSwagger();
        generator.global(globalConfig.build());
    }

    /**
     * 各个包配置
     *
     * @param generator :
     */
    private static void configPackage(AutoGenerator generator) {
        PackageConfig.Builder packageConfig = new PackageConfig.Builder();
        packageConfig.moduleName(MODULE_NAME);
        packageConfig.parent("main");//主路径
        packageConfig.controller("java." + BASE_PACKAGE + ".controller");
        packageConfig.service("java." + BASE_PACKAGE + ".service");
        packageConfig.serviceImpl("java." + BASE_PACKAGE + ".service.impl");
        packageConfig.entity("java." + BASE_PACKAGE + ".entity");
        packageConfig.mapper("java." + BASE_PACKAGE + ".mapper");
        packageConfig.xml("resources." + BASE_PACKAGE + ".mapper");
        generator.packageInfo(packageConfig.build());
    }

    /**
     * 策略配置
     *
     * @param generator :
     */
    private static void configStrategy(AutoGenerator generator) {
        // 策略配置
        StrategyConfig.Builder strategy = new StrategyConfig.Builder();
        //全局大写命名
        //strategy.setCapitalMode(true)
        //全局下划线命名
        //strategy.setDbColumnUnderline(true)
        //表的前缀
        //strategy.setTablePrefix();
        //生成哪些表
        strategy.addInclude(TABLES);
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);

        //生成的文件名字定义，%s 会自动填充表实体属性
        strategy.mapperBuilder().formatMapperFileName("%sMapper");
        strategy.mapperBuilder().enableBaseColumnList();
        strategy.mapperBuilder().enableBaseResultMap();
        strategy.mapperBuilder().enableFileOverride();

        strategy.entityBuilder().formatFileName("%s");
        strategy.entityBuilder().naming(NamingStrategy.underline_to_camel);
        strategy.entityBuilder().columnNaming(NamingStrategy.underline_to_camel);
        //是否使用lombok
        strategy.entityBuilder().enableLombok();
        //设置布尔类型字段是否去掉is前缀
        strategy.entityBuilder().enableRemoveIsPrefix();
        strategy.entityBuilder().enableFileOverride();

        strategy.serviceBuilder().formatServiceFileName("%sService");
        strategy.serviceBuilder().formatServiceImplFileName("%sServiceImpl");
        strategy.serviceBuilder().enableFileOverride();

        strategy.controllerBuilder().formatFileName("%sController");
        strategy.controllerBuilder().enableHyphenStyle();
        //设置是否restful控制器
        strategy.controllerBuilder().enableRestStyle();
        strategy.controllerBuilder().enableFileOverride();
        generator.strategy(strategy.build());
    }

    /**
     * 自定义配置
     *
     * @param generator   :
     * @param projectPath :
     */
    private static void configCustom(AutoGenerator generator, final String projectPath) {
        // 自定义配置
        InjectionConfig.Builder cfg = new InjectionConfig.Builder();

        // 模版放到resources目录下的templates目录中，默认模板引擎是velocity
        String templatePath = "/templates/entity.vo.java.vm";

        // 自定义输出配置
        List<CustomFile> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        CustomFile.Builder builder = new CustomFile.Builder();
        focList.add(builder.build());
        builder.templatePath(templatePath);
        builder.filePath(projectPath.concat("/src/main/").concat(MODULE_NAME).concat("/java/com/mybatis/demo/vo/"));
        //自定义输出文件名 ， 如果 Entity 设置了前后缀、此处 xml 的名称会跟着发生变化
        builder.fileName("VO".concat(StringPool.DOT_JAVA));
       /* cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });*/
        cfg.customFile(focList);
        Map<String, Object> map = new HashMap<>();
        map.put("needCommonWhere",true);
        map.put("needBaseInsert",true);
        map.put("dateTime",new Date());
        map.put("needExtensionAnnotation",true);
        cfg.customMap(map);
        generator.injection(cfg.build());
    }

    /**
     * 模版引擎配置
     *
     * @param generator :
     */
    private static void configTemplate(AutoGenerator generator) {
        //模板引擎配置 默认是VelocityTemplateEngine
        TemplateConfig.Builder templateConfig = new TemplateConfig.Builder();

        templateConfig.controller("/templates/controller.java.vm")
                .service("/templates/service.java.vm")
                .serviceImpl("/templates/serviceImpl.java.vm")
                .entity("/templates/entity.java.vm")
                .mapper("/templates/mapper.java.vm")
                .xml("/templates/mapper.xml.vm");
        generator.template(templateConfig.build());
    }
}