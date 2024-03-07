package com.zz.psmback.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zz.psmback.dao")
@SpringBootApplication(scanBasePackages = {"com.zz.psmback.service","com.zz.psmback.web.controller","com.zz.psmback.dao",
        "com.zz.psmback.common",})
public class PsmbackWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(PsmbackWebApplication.class, args);
    }

}
