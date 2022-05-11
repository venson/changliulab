package com.venson;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.junit.jupiter.api.Test;
import java.util.Collections;

public class CodeGenerator {

    @Test
    public void generator() {
        String projectPath = System.getProperty("user.dir");

//       教师表
//        String mysqlTable = "edu_teacher";
//      课程表
//        String mysqlTable = "edu_subject";
        String mysqlTable = "edu_course,edu_course_description,edu_chapter,edu_video";
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
                            .mapper("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapper, projectPath + "/src/main/java/com/venson/eduservice/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(mysqlTable) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")
                            .controllerBuilder()
                                .enableHyphenStyle()
                                .enableRestStyle()
                            .entityBuilder()
                                .enableLombok()
                            .addTableFills(new Column("gmt_create",FieldFill.INSERT))
                            .addTableFills(new Column("gmt_update",FieldFill.INSERT_UPDATE))
                            .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImp")
                    ; // 设置过滤表前缀
                })
                .execute();
    }

//    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
//            .Builder("jdbc:mysql://localhost:3306/changliulab", "root", "123")
//            .build();
//
}
