package com.zz.psmback.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zz.psmback.dao")
@SpringBootApplication(scanBasePackages = {"com.zz.psmback"})
public class PsmbackWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(PsmbackWebApplication.class, args);
    }

}
