package com.codeking.boot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.codeking.boot.repositry.HospitalRepository;
import com.codeking.boot.service.DepartmentService;
import com.codeking.boot.service.HospitalService;
import com.codeking.boot.service.HospitalSetService;
import com.codeking.yygh.cmn.client.DictFeignClient;
import com.codeking.yygh.common.exception.YyghException;
import com.codeking.yygh.common.helper.HttpRequestHelper;
import com.codeking.yygh.common.result.ResultCodeEnum;
import com.codeking.yygh.enums.DictEnum;
import com.codeking.yygh.model.hosp.Hospital;
import com.codeking.yygh.vo.hosp.HospitalQueryVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private DictFeignClient dictFeignClient;

    @Autowired
    private DepartmentService departmentService;



    @Override
    public void save(Map<String, Object> paramMap) {
        log.info(JSONObject.toJSONString(paramMap));
        //校验hoscode 验证签名
        verifyHoscodeAndSignKey(paramMap);
        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoDataString = (String) paramMap.get("logoData");
        if (!StringUtils.isEmpty(logoDataString)) {
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
    public Hospital getById(String id) {
        return hospitalRepository.getHospitalById(id);
    }

    @Override
    public Hospital search(Map<String, Object> paramMap) {
        //校验hoscode 验证签名
        verifyHoscodeAndSignKey(paramMap);
        Hospital hospital = getByHoscode((String) paramMap.get("hoscode"));
        return hospital;
    }

    @Override
    public Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        //0为第一页
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Hospital hospital = new Hospital();
        //
        BeanUtils.copyProperties(hospitalQueryVo, hospital);
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)//改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true);//改变默认大小写忽略方式：忽略大小写
        //创建实例
        Example<Hospital> example = Example.of(hospital, matcher);
        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);

        // 通过流的方式 增加数据
        pages.getContent().stream().forEach(item -> {
            packHospital(item);
        });
        return pages;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        if (status.intValue() == 0 || status.intValue() == 1) {
            Hospital hospital = hospitalRepository.findById(id).get();
            hospital.setStatus(status);
            hospital.setUpdateTime(new Date());
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Map<String, Object> show(String id) {
        Map<String, Object> result = new HashMap<>();
        Hospital hospital = this.packHospital(this.getById(id));
        result.put("hospital", hospital);

        //单独处理更直观
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return result;

    }

    /**
     * 根据医院编号获取医院名称接口
     * @param hoscode
     * @return
     */

    @Override
    public String getHospName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        if (hospital != null) {
            return hospital.getHosname();
        }
        return "";
    }

    @Override
    public List<Hospital> findByHosname(String hosname) {
        return hospitalRepository.findHospitalByHosnameLike(hosname);
    }

    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> result = new HashMap<>();
        //医院详情
        Hospital hospital = this.packHospital(this.getByHoscode(hoscode));
        result.put("hospital", hospital);
        //预约规则
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return result;
    }

    /**
     * 封装数据
     *
     * @param hospital
     * @return
     */

    private Hospital packHospital(Hospital hospital) {
        String hostypeString = dictFeignClient.getName(DictEnum.HOSTYPE.getDictCode(), hospital.getHostype());
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("hostypeString", hostypeString);
        hospital.getParam().put("fullAddress", provinceString + cityString + districtString + hospital.getAddress());
        return hospital;
    }


    public void verifyHoscodeAndSignKey(Map<String, Object> paramMap) {
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
