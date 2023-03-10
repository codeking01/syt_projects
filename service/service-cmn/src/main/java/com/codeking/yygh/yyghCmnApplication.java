package com.codeking.yygh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : codeking
 * @create : 2023/2/4 22:02
 */
@SpringBootApplication
@ComponentScan(value = "com.codeking")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.codeking")
public class yyghCmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(yyghCmnApplication.class, args);
    }
}
