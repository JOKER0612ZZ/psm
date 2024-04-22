package com.zz.psmback.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.zz.psmback.dao")
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.zz.psmback"})
public class PsmbackWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(PsmbackWebApplication.class, args);
    }

}
