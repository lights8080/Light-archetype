package com.light.config.feign;

import com.light.config.logger.LoggerConfig;
import feign.Request;
import feign.Response;
import feign.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static feign.Util.decodeOrDefault;

public class LightFeignLogger extends feign.Logger {
    private static final Logger logger = LoggerFactory.getLogger("FeignLog");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    //    private final Logger logger;
    private LoggerConfig.Setting setting;

    public LightFeignLogger(Logger logger, LoggerConfig.Setting setting) {
//        this.logger = logger;
        this.setting = setting;
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        if (logger.isInfoEnabled() && setting.isEnabled()) {
            if (!setting.getSkipMethodSigns().contains(configKey)) {
                logger.info("FEIGN-REQ-[{}] {} --> {} {}", configKey, request.body() != null ? new String(request.body(), UTF_8) : "", request.httpMethod(), request.url());
            }
        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        if (response.body() != null) {
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
            if (logger.isInfoEnabled() && setting.isEnabled()) {
                if (!setting.getSkipMethodSigns().contains(configKey)) {
                    if (setting.getIgnoreResponseDataMethodSigns().contains(configKey)) {
                        logger.info("FEIGN-RESP-[{}] IGNORE_DATA size:{} spend:{}ms", configKey, (bodyData != null ? bodyData.length : 0), elapsedTime);
                    } else {
                        logger.info("FEIGN-RESP-[{}] {} size:{} spend:{}ms", configKey, decodeOrDefault(bodyData, UTF_8, ""), (bodyData != null ? bodyData.length : 0), elapsedTime);
                    }
                }
            }
            return response.toBuilder().body(bodyData).build();
        } else {
            if (logger.isInfoEnabled() && setting.isEnabled()) {
                if (!setting.getSkipMethodSigns().contains(configKey)) {
                    logger.info("FEIGN-RESP-[{}] size:0 spend:{}ms", configKey, elapsedTime);
                }
            }
            return response;
        }
    }

    @Override
    protected IOException logIOException(String configKey, Level logLevel, IOException ioe, long elapsedTime) {
        if (logger.isErrorEnabled() && setting.isEnabled()) {
            logger.error("FEIGN-RESP-[{}] {}: {}", configKey, ioe.getClass().getSimpleName(), ioe.getMessage());
        }
        return ioe;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(String.format(methodTag(configKey) + format, args));
        }
    }
}
