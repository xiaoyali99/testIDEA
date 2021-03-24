package com.linzc.security1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@MapperScan("com.linzc.security1.mapper")
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class Security1Application {

    public static void main(String[] args) {
        SpringApplication.run(Security1Application.class, args);
    }
}
