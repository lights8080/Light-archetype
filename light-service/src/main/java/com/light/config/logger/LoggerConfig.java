package com.light.config.logger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(ignoreInvalidFields = true, prefix = LoggerConfig.PREFIX)
public class LoggerConfig {

    public static final String PREFIX = "light.logger";

    private Setting feign = new Setting();
    private Setting rest = new Setting();
    private Setting controller = new Setting();

    public Setting getFeign() {
        return feign;
    }

    public void setFeign(Setting feign) {
        this.feign = feign;
    }

    public Setting getRest() {
        return rest;
    }

    public void setRest(Setting rest) {
        this.rest = rest;
    }

    public Setting getController() {
        return controller;
    }

    public void setController(Setting controller) {
        this.controller = controller;
    }

    public static class Setting {

        private boolean enabled = true;

        private List<String> ignoreResponseDataMethodSigns = new ArrayList<>();

        private List<String> skipMethodSigns = new ArrayList<>();

        private boolean showSpendms = false;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<String> getIgnoreResponseDataMethodSigns() {
            return ignoreResponseDataMethodSigns;
        }

        public void setIgnoreResponseDataMethodSigns(List<String> ignoreResponseDataMethodSigns) {
            this.ignoreResponseDataMethodSigns = ignoreResponseDataMethodSigns;
        }

        public List<String> getSkipMethodSigns() {
            return skipMethodSigns;
        }

        public void setSkipMethodSigns(List<String> skipMethodSigns) {
            this.skipMethodSigns = skipMethodSigns;
        }

        public boolean isShowSpendms() {
            return showSpendms;
        }

        public void setShowSpendms(boolean showSpendms) {
            this.showSpendms = showSpendms;
        }
    }

}
