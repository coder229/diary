package com.github.coder229.dairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Created by scott on 9/2/2015.
 */
@SpringBootApplication
public class DiaryApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DiaryApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DiaryApplication.class);
    }
}
