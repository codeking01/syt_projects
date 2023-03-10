package com.codeking.yygh.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.codeking")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.codeking")
public class serviceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(serviceUserApplication.class,args);
    }
}
