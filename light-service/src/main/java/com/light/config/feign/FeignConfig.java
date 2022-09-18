package com.light.config.feign;

import com.light.config.logger.LoggerConfig;
import feign.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    @ConditionalOnMissingBean(LoggerConfig.class)
    LoggerConfig loggerConfig() {
        return new LoggerConfig();
    }

    @Bean
    Logger.Level feignLevel() {
        return Logger.Level.NONE;
    }

    @Bean
    FeignLoggerFactory infoFeignLoggerFactory(LoggerConfig loggerConfig) {
        return new LightFeignLoggerFactory(loggerConfig.getFeign());
    }

}
