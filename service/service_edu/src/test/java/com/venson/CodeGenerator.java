package com.venson;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
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
//        String mysqlTable = "edu_review";
        String mysqlTable = "edu_activity_published";
//        String mysqlTable = "edu_scholar_citation,edu_member_scholar";
//        String mysqlTable = "edu_research,edu_methodology,edu_activity,edu_activity_html,edu_activity_markdown";
//        String mysqlTable = "edu_research,edu_methodology,edu_activity,edu_activity_html,edu_activity_markdown";
//        String mysqlTable = "edu_chapter_markdown,edu_chapter_published_md,edu_section_markdown,edu_section_published_md";
//        String mysqlTable = "edu_course,edu_course_published,"+
//                "edu_chapter,edu_chapter_published,"+
//                "edu_section,edu_section_published,"+
//                "edu_chapter_section_content,edu_chapter_section_content_published,"+
//                "edu_course_description,edu_course_description_published";
//        AutoGenerator autoGenerator = new AutoGenerator(DATA_SOURCE_CONFIG);
//        autoGenerator.strategy(new StrategyConfig.Builder()
//                .controllerBuilder()
//                .enableHyphenStyle()
//                .build());
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/changliulab", "root", "123")
                .globalConfig(builder -> {
                    builder.author("venson") // 设置作者
                            .dateType(DateType.ONLY_DATE)
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
//                            .addTablePrefix("edu_")
                            .controllerBuilder()
                                .enableHyphenStyle()
                                .enableRestStyle()
                            .entityBuilder()
                            .idType(IdType.ASSIGN_ID)
                            .versionColumnName("version")
                            .logicDeleteColumnName("is_deleted")
                                .enableLombok()
                            .addTableFills(new Column("gmt_create",FieldFill.INSERT))
                            .addTableFills(new Column("gmt_modified",FieldFill.INSERT_UPDATE))
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
