package com.venson.servicebase;

import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("Chang Liu lab")
                    .description("")
                    .version("1.0")
                    .build();
        }

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.OAS_30)
                    .apiInfo(apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.regex("^((?!error).)*$"))
                    .paths(PathSelectors.regex("^((?!admin).)*$"))
                    .build();
        }
    }
