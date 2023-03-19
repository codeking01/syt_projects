package com.codeking.springcloudsentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel")
public class sentinelController {
    @GetMapping("/demo1")
    public String test() {
        return "sentinel的 demo1";
    }

    @GetMapping("/demo2")
    public String test2() {
        return "sentinel的 demo2....";
    }
}
