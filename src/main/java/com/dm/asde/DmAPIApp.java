package com.dm.asde;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * This is the main class. It is located at the root com.gcs package so that it can scan spring annotated classes.
 * It extends {@link SpringBootServletInitializer} and overrides its configure method to make the application war-deployable.
 */
@ComponentScan
@EnableAutoConfiguration
public class DmAPIApp extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DmAPIApp.class);
    }


    public static void main(String... args) {
        SpringApplication.run(DmAPIApp.class, args);
    }
}