package com.light.config.rest;

import com.light.config.logger.LoggerConfig;
import org.apache.http.client.HttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @ConditionalOnMissingBean(LoggerConfig.class)
    LoggerConfig loggerConfig() {
        return new LoggerConfig();
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        httpComponentsClientHttpRequestFactory.setConnectTimeout(5000);
        httpComponentsClientHttpRequestFactory.setReadTimeout(60000);
        return httpComponentsClientHttpRequestFactory;
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory httpRequestFactory, LoggerConfig loggerConfig) {
        BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(httpRequestFactory);
        RestTemplate restTemplate = new RestTemplate(bufferingClientHttpRequestFactory);
        restTemplate.getInterceptors().add(new RestTemplateLogInterceptor(loggerConfig.getRest()));
        return restTemplate;
    }
}
