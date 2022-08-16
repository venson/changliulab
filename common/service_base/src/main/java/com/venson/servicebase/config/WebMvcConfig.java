package com.venson.servicebase.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();
//        FastJsonConfig fjc = new FastJsonConfig();
//        // 配置序列化策略
//        fjc.setSerializerFeatures(SerializerFeature.BrowserCompatible);
//        SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
//        fjc.setSerializerFeatures(SerializerFeature.BrowserCompatible);
//        fastJsonConverter.setFastJsonConfig(fjc);
//        converters.add(fastJsonConverter);
//    }
//}
