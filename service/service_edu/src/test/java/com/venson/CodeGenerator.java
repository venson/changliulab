package com.venson;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.junit.jupiter.api.Test;
import java.util.Collections;

public class CodeGenerator {

    @Test
    public void generator() {
        String projectPath = System.getProperty("user.dir");
//        AutoGenerator autoGenerator = new AutoGenerator(DATA_SOURCE_CONFIG);
//        autoGenerator.strategy(new StrategyConfig.Builder()
//                .controllerBuilder()
//                .enableHyphenStyle()
//                .build());
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/changliulab", "root", "123")
                .globalConfig(builder -> {
                    builder.author("baomidou") // 设置作者
                            .dateType(DateType.ONLY_DATE)
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.venson") // 设置父包名
                            .moduleName("eduservice") // 设置父包模块名
                            .service("service")
                            .entity("entity")
                            .controller("controller")
                            .mapper("com/venson/eduservice/mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapper, projectPath + "/src/main/java/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("edu_teacher") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")
                            .controllerBuilder()
                                .enableHyphenStyle()
                                .enableRestStyle()
                            .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImp")
                    ; // 设置过滤表前缀
                })
                .execute();
    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/changliulab", "root", "123")
            .build();

}
