package com.light.config;

import com.purgeteam.dynamic.config.starter.event.ActionConfigEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppConfigListener implements ApplicationListener<ActionConfigEvent> {

    @Override
    public void onApplicationEvent(ActionConfigEvent event) {
        log.info("配置变更");
        log.info(event.getPropertyMap().toString());
    }
}