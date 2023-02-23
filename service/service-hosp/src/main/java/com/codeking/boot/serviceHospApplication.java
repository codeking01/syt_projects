package com.codeking.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : codeking
 * @create : 2023/1/30 23:13
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.codeking")
@EnableDiscoveryClient // 启动nacos
@EnableFeignClients(basePackages = "com.codeking")
public class serviceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(serviceHospApplication.class, args);
    }
}
