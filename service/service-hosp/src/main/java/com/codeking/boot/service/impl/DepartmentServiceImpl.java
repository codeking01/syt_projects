package com.codeking.boot.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.codeking.boot.repositry.DepartmentRepository;
import com.codeking.boot.service.DepartmentService;
import com.codeking.boot.service.HospitalSetService;
import com.codeking.yygh.common.exception.YyghException;
import com.codeking.yygh.common.helper.HttpRequestHelper;
import com.codeking.yygh.common.result.ResultCodeEnum;
import com.codeking.yygh.model.hosp.Department;
import com.codeking.yygh.vo.hosp.DepartmentQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author : codeking
 * @create : 2023/2/8 20:25
 */
@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    // 注入 DepartmentRepository
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private HospitalSetService hospitalSetService;


    @Override
    public void save(Map<String, Object> paramMap) {
        // 校验签名
        verifyHoscodeAndSignKey(paramMap);

        Department department = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Department.class);
        Department targetDepartment = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());
        if (null != targetDepartment) {
            //copy不为null的值，该方法为自定义方法
            BeanUtils.copyProperties(department, targetDepartment, Department.class);
            departmentRepository.save(targetDepartment);
        } else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> selectPage(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        //0为第一页
        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);

        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        //创建实例
        Example<Department> example = Example.of(department, matcher);
        Page<Department> pages = departmentRepository.findAll(example, pageable);
        return pages;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if (null != department) {
            //departmentRepository.delete(department);
            departmentRepository.deleteById(department.getId());
        }
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
