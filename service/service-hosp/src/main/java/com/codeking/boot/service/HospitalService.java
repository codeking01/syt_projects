package com.codeking.boot.service;

import com.codeking.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {

    void save(Map<String, Object> paramMap);

    /**
     * 查询医院
     * @param hoscode
     * @return
     */
    Hospital getByHoscode(String hoscode);

    Hospital search(Map<String, Object> paramMap);
}
