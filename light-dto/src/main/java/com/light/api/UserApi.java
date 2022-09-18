package com.light.api;

import com.light.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "light-service")
public interface UserApi {

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    String echo(@PathVariable String string);

    @GetMapping("/user/list")
    List<User> list();

    @GetMapping("/user/get")
    User get(@RequestParam("name") String name);

    @PostMapping(value = "/user/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Boolean add(@RequestBody User user);

}
