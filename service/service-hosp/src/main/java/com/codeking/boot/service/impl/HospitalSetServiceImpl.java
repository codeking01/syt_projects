package com.codeking.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeking.boot.mapper.HospitalSetMapper;
import com.codeking.boot.service.HospitalSetService;
import com.codeking.yygh.model.hosp.HospitalSet;
import org.springframework.stereotype.Service;

/**
 * @author : codeking
 * @create : 2023/1/30 23:24
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {
}
