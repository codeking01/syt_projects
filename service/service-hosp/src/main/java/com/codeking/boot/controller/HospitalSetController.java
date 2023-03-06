package com.codeking.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codeking.boot.service.HospitalSetService;
import com.codeking.yygh.common.exception.YyghException;
import com.codeking.yygh.common.result.Result;
import com.codeking.yygh.common.utils.MD5;
import com.codeking.yygh.model.hosp.HospitalSet;
import com.codeking.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @author : codeking
 * @create : 2023/1/30 23:26
 */
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@ResponseBody
//@CrossOrigin // 解决跨域问题 配置网关可以注释，否则可能出异常
public class HospitalSetController {
    // 注入service
    @Autowired
    //@Resource
    private HospitalSetService hospitalSetService;

    // 查询内容
    @ApiOperation(value = "获取所有医院设置信息")
    @GetMapping("findAll")
    @ResponseBody
    public Result<?> findAllHospitalSet() {
        /*try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new YyghException("错误",500);
        }*/
        //调用service的方法
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    // 逻辑删除医院的设置
    @ApiOperation(value = "逻辑删除医院信息")
    @DeleteMapping("{id}")
    public Result<?> deleteHospitalSet(@PathVariable("id") Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //3 条件查询带分页
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result<?> findPageHospSet(@PathVariable long current,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current, limit);
        //构建条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();//医院名称
        String hoscode = hospitalSetQueryVo.getHoscode();//医院编号
        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }
        //调用方法实现分页查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);
        //返回结果
        return Result.ok(pageHospitalSet);
    }

    // 添加医院设置
    @ApiOperation(value = "添加医院设置")
    @PostMapping("saveHospitalSet")
    public Result<?> saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //5 根据id获取医院设置
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    //6 修改医院设置
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //7 批量删除医院设置
    @DeleteMapping("batchRemove")
    public Result batchDeleteHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }
    //8 医院设置锁定和解锁
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    //9 发送签名秘钥
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }

}
