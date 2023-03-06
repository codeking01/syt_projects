package com.codeking.boot.service;

import com.codeking.yygh.model.hosp.Hospital;
import com.codeking.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface HospitalService {

    void save(Map<String, Object> paramMap);

    /**
     * 查询医院
     * @param hoscode
     * @return
     */
    Hospital getByHoscode(String hoscode);

    Hospital getById(String id);

    Hospital search(Map<String, Object> paramMap);
    /**
     * 分页查询
     * @param page 当前页码
     * @param limit 每页记录数
     * @param hospitalQueryVo 查询条件
     * @return
     */
    Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);
    /**
     * 更新上线状态
     */
    void updateStatus(String id, Integer status);
    /**
     * 医院详情
     * @param id
     * @return
     */
    Map<String, Object> show(String id);

    String getHospName(String hoscode);
}
