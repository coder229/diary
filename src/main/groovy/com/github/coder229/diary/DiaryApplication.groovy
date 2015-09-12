package com.github.coder229.diary

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer

/**
 * Created by scott on 9/2/2015.
 */
@SpringBootApplication
class DiaryApplication extends SpringBootServletInitializer {

    static void main(String[] args) {
        SpringApplication.run(DiaryApplication.class, args)
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DiaryApplication.class)
    }
}
