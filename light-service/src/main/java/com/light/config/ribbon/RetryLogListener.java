package com.light.config.ribbon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryContext;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

public class RetryLogListener extends RetryListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger("RibbonRetryLog");

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        logger.warn("LIGHT-RETRY serviceId:{} count:{} exhausted:{} msg:{}", ((LoadBalancedRetryContext) context).getServiceInstance().getServiceId(), context.getRetryCount(), context.isExhaustedOnly(), throwable.getMessage());
    }
}
