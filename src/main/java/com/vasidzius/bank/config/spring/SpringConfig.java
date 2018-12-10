package com.vasidzius.bank.config.spring;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class SpringConfig {

    @Bean
    public Executor taskExecutor() {
        return Executors.newCachedThreadPool(threadFactory());
    }

    private ThreadFactory threadFactory() {
        return new ThreadFactoryBuilder().setNameFormat("my-thread-%d").build();
    }
}
