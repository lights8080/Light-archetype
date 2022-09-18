package com.light.controller;


import com.light.config.AppConfig;
import com.light.api.UserApi;
import com.light.model.User;
import com.light.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class Demo1Controller {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserApi userApi;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private LoadBalancerClient loadBalancer;
    @Autowired
    private UserService userService;


    @GetMapping("/echo/{string}")
    public String echo(@PathVariable String string) {
        return "lights-service echo: " + string;
    }

    @PostMapping(value = "/user/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean userAdd(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/user/get")
    public User userGet(@RequestParam("name") String name) {
        return userApi.get(name);
    }

    @GetMapping(value = "/user/list")
    public List<User> userList() {
//        return userApi.list();
        return restTemplate.getForObject("http://lights-service/user/list", List.class);
    }

    @GetMapping("/config/username")
    public String configUsername() {
        return appConfig.getUsername();
    }

    @GetMapping("/sleep")
    public String sleep() {
        try {
            long ms = (new Random().nextInt(10) + 1) * 1000;
            Thread.sleep(ms);
            System.out.println("sleep " + ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @GetMapping("/lb")
    public String lb() {
        ServiceInstance choose = loadBalancer.choose("lights-service");
        log.info(choose.toString());
        return choose.getHost() + ":" + choose.getPort();
    }

    @PostMapping("/redis/set")
    public Boolean redisSet(String name, String value) {
        stringRedisTemplate.opsForValue().set(name, value, 1, TimeUnit.MINUTES);
        return Boolean.TRUE;
    }

    @GetMapping("/redis/get")
    public String redisGet(String name) {
        return stringRedisTemplate.opsForValue().get(name);
    }
}
