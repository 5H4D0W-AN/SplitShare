package com.amitnehra.configuration;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    public LoggingConfig() {
        ch.qos.logback.classic.Logger springLogger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.springframework");
        springLogger.setLevel(ch.qos.logback.classic.Level.DEBUG);


        ch.qos.logback.classic.Logger jacksonLogger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.fasterxml.jackson");
        jacksonLogger.setLevel(ch.qos.logback.classic.Level.DEBUG);
    }
}
