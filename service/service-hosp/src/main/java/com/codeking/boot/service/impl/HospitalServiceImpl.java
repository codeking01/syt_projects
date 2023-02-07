package com.codeking.boot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.codeking.boot.repositry.HospitalRepository;
import com.codeking.boot.service.HospitalService;
import com.codeking.boot.service.HospitalSetService;
import com.codeking.yygh.common.exception.YyghException;
import com.codeking.yygh.common.helper.HttpRequestHelper;
import com.codeking.yygh.common.result.ResultCodeEnum;
import com.codeking.yygh.model.hosp.Hospital;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author : codeking
 * @create : 2023/2/6 23:06
 */
@Service
@Log4j2
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalSetService hospitalSetService;


    @Override
    public void save(Map<String, Object> paramMap) {
        log.info(JSONObject.toJSONString(paramMap));
        //校验hoscode 验证签名
        verifyHoscodeAndSignKey(paramMap);
        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoDataString = (String)paramMap.get("logoData");
        if(!StringUtils.isEmpty(logoDataString)) {
            String logoData = logoDataString.replaceAll("", "+");
            paramMap.put("logoData", logoData);
        }
        Hospital hospital = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Hospital.class);
        //判断是否存在
        Hospital targetHospital = hospitalRepository.getHospitalByHoscode(hospital.getHoscode());
        // 存在
        if (null != targetHospital) {
            hospital.setStatus(targetHospital.getStatus());
            hospital.setCreateTime(targetHospital.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        } else {
            //0：未上线 1：已上线
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        return hospitalRepository.getHospitalByHoscode(hoscode);
    }

    @Override
    public Hospital search(Map<String, Object> paramMap) {
        //校验hoscode 验证签名
        verifyHoscodeAndSignKey(paramMap);
        Hospital hospital = getByHoscode((String) paramMap.get("hoscode"));
        return hospital;
    }

    private void verifyHoscodeAndSignKey(Map<String, Object> paramMap) {
        //必须参数校验 略
        String hoscode = (String) paramMap.get("hoscode");
        if (StringUtils.isEmpty(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验

        if (!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
    }
}
