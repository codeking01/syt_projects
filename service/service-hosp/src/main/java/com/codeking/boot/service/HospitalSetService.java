package com.codeking.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codeking.yygh.model.hosp.HospitalSet;
import com.codeking.yygh.vo.order.SignInfoVo;
import org.springframework.stereotype.Service;

/**
 * @author : codeking
 * @create : 2023/1/30 23:22
 */

public interface HospitalSetService extends IService<HospitalSet> {
    /**
     * 获取签名key
     * @param hoscode
     * @return hospitalSet.getSignKey()
     */
    String getSignKey(String hoscode);

    //获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);

}
