package com.codeking.yygh.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : codeking
 * @create : 2023/2/5 10:30
 */
@SpringBootApplication(scanBasePackages = "com.codeking")
public class ServiceUtilApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUtilApp.class, args);
    }
}
