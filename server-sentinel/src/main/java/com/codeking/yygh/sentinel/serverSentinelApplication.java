package com.codeking.yygh.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.codeking")
public class serverSentinelApplication {
    public static void main(String[] args) {
        SpringApplication.run(serverSentinelApplication.class, args);
    }
 
}