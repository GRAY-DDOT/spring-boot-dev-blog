package com.ddot.springbootdevblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// create_at, updated_at 자동 업데이트
@EnableJpaAuditing
@SpringBootApplication
public class SpringBootDevBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDevBlogApplication.class, args);
    }

}
