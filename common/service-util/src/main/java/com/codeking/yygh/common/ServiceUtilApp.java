package com.codeking.yygh.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

/**
 * @author : codeking
 * @create : 2023/2/5 10:30
 */
//@SpringBootApplication(scanBasePackages = "com.codeking")
@Repository
public class ServiceUtilApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUtilApp.class, args);
    }
}
