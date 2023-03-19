package com.codeking.yygh.sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/sentinel")
public class DemoController {
 
    @GetMapping("demo1")
    public String demo1() {
        return "sentinel1";
    }
 
    @GetMapping("demo2")
    public String demo2() {
        return "sentinel2";
    }
 
}