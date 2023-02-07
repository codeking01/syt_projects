package com.codeking.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeking.boot.mapper.HospSetMapper;
import com.codeking.boot.service.HospitalSetService;
import com.codeking.yygh.common.exception.YyghException;
import com.codeking.yygh.common.result.ResultCodeEnum;
import com.codeking.yygh.model.hosp.HospitalSet;
import org.springframework.stereotype.Service;

/**
 * @author : codeking
 * @create : 2023/1/30 23:24
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospSetMapper, HospitalSet> implements HospitalSetService {
    /**
     *
     * @param hoscode
     * @return hospitalSet.getSignKey()
     */
    @Override
    public String getSignKey(String hoscode) {
        HospitalSet hospitalSet = this.getByHoscode(hoscode);
        if (null == hospitalSet) {
            throw new YyghException(ResultCodeEnum.HOSPITAL_OPEN);
        }
        if (hospitalSet.getStatus().intValue() == 0) {
            throw new YyghException(ResultCodeEnum.HOSPITAL_LOCK);
        }
        return hospitalSet.getSignKey();
    }

    private HospitalSet getByHoscode(String hoscode) {
        return baseMapper.selectOne(new QueryWrapper<HospitalSet>().eq("hoscode",hoscode));
    }
}
