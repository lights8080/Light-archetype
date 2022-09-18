package com.light.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.light.entity.DemoUser;
import com.light.mapper.DemoUserMapper;
import com.light.model.User;
import com.light.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DemoUserMapper demoUserMapper;

    @Override
    public Boolean add(com.light.model.User user) {
        DemoUser demoUserEntity = new DemoUser();
        demoUserEntity.setName(user.getName());
        demoUserEntity.setAge(user.getAge());
        int insert = demoUserMapper.insert(demoUserEntity);
        log.info("user add. tostring:{} {}", user.toString(), insert);
        return true;
    }

    @Override
    public List<User> list() {
        IPage<DemoUser> userPage = new Page<>();
        List<User> list = new ArrayList<>();
        QueryWrapper<DemoUser> queryWrapper = new QueryWrapper<>();
        userPage = demoUserMapper.selectPage(userPage, queryWrapper);
        log.info(JSON.toJSONString(userPage));
        for (DemoUser userEntity : userPage.getRecords()) {
            User user = new User();
            BeanUtils.copyProperties(userEntity, user);
            list.add(user);
        }
        return list;
    }

}
