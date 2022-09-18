package com.light;


import com.purgeteam.dynamic.config.starter.annotation.EnableDynamicConfigEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableDynamicConfigEvent
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

