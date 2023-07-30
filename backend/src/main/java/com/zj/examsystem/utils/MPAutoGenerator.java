package com.zj.examsystem.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MPAutoGenerator {
    public static void main(String[] args) {
        // 创建AutoGenerator
        AutoGenerator autoGenerator = new AutoGenerator();

        // 设置全局配置
        GlobalConfig global = new GlobalConfig();
        global.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        global.setMapperName("%sMapper");
        global.setServiceImplName("%sServiceImpl");
        global.setServiceName("%sService");
        global.setControllerName("%sController");
        global.setIdType(IdType.AUTO);
        autoGenerator.setGlobalConfig(global);

        // 设置数据源DataSource
        DataSourceConfig dataSource = new DataSourceConfig();
        dataSource.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/exam_online_system");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        autoGenerator.setDataSource(dataSource);

        // 设置Package信息
        PackageConfig pack = new PackageConfig();
        // 设置包名，该package下有mapper、service、controller等文件夹及代码
        pack.setModuleName("auto");
        // 设置父包名
        pack.setParent("com.zj.examsystem");
        autoGenerator.setPackageInfo(pack);

        // 设置策略
        StrategyConfig strategy = new StrategyConfig();
        // underline_to_camel：驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        autoGenerator.setStrategy(strategy);

        // 执行自动生成
        autoGenerator.execute();
    }
}
