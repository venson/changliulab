package com.venson.servicebase.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class JacksonConfig {
    @Value("${spring.jackson.date-format}")
    private String dateFormat;
    /**
     * 统一注解，解决前后端交互Long类型精度丢失的问题
     */
//    @Bean
//    public ObjectMapper jacksonObjectMapper() {
//        ObjectMapper objectMapper = JsonMapper.builder().build();
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.SIMPLIFIED_CHINESE);
////        objectMapper.registerModule(new JavaTimeModule().addSerializer(LocalDateTime.class,
////                new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME)));
//        objectMapper.registerModule(new JavaTimeModule()
//                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter))
//                .addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(formatter)));
//        objectMapper.registerModule(simpleModule);
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
//        return objectMapper;
//    }
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.SIMPLIFIED_CHINESE);
        return builder -> builder.serializerByType(Long.class,ToStringSerializer.instance)
                .deserializerByType(LocalDateTime.class,new LocalDateTimeDeserializer(formatter))
                .serializerByType(LocalDateTime.class,new LocalDateTimeSerializer(formatter))
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(JsonMapper.builder().addModules(new JavaTimeModule()).build());
    }

}
