package com.light.config.feign;

import com.light.config.logger.LoggerConfig;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignLoggerFactory;

public class LightFeignLoggerFactory implements FeignLoggerFactory {

    private LoggerConfig.Setting setting;

    public LightFeignLoggerFactory(LoggerConfig.Setting setting) {
        this.setting = setting;
    }

    @Override
    public feign.Logger create(Class<?> type) {
        return new LightFeignLogger(LoggerFactory.getLogger(type), this.setting);
    }
}
