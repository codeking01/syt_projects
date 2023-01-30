package com.codeking.boot.controller;

import com.codeking.boot.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : codeking
 * @create : 2023/1/30 23:26
 */
@RestController
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

}
