package com.light.config.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class HttpMessageConverterConfig {

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        SerializerFeature[] serializerFeatures = new SerializerFeature[]{
                // Date的日期转换器
                SerializerFeature.WriteDateUseDateFormat,
                // 循环引用
                SerializerFeature.DisableCircularReferenceDetect
        };

        fastJsonConfig.setSerializerFeatures(serializerFeatures);
        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
        fastConverter.setFastJsonConfig(fastJsonConfig);

        List<MediaType> fastjsonSupportedMediaTypes = new ArrayList<>();
        fastjsonSupportedMediaTypes.add(MediaType.TEXT_PLAIN);
        fastjsonSupportedMediaTypes.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(fastjsonSupportedMediaTypes);

        return new HttpMessageConverters(fastConverter);
    }

    private static class LightFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {
        private static final Logger logger = LoggerFactory.getLogger("ControllerLog");

        @Override
        public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException,
                HttpMessageNotReadableException {
            Object obj = super.read(type, contextClass, inputMessage);
            if (logger.isInfoEnabled()) {
                logger.info("LIGHT-REQ-[{}] {}", type.getTypeName(), JSON.toJSONString(obj));
            }
            return obj;
        }

        @Override
        public void write(Object t, Type type, MediaType contentType, HttpOutputMessage outputMessage)
                throws IOException, HttpMessageNotWritableException {
            if (type.getTypeName().endsWith("ListResp")) {
                if (logger.isInfoEnabled()) {
                    logger.info("LIGHT-RESP-[{}] {}", type.getTypeName(), "LIST_IGNORE");
                }
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("LIGHT-RESP-[{}] {}", type.getTypeName(), JSON.toJSONString(t));
                }
            }
            super.write(t, type, contentType, outputMessage);
        }
    }

}
