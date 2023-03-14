package com.codeking.yygh.msm.service;

import com.codeking.yygh.vo.msm.MsmVo;

public interface MsmService {

    //发送手机验证码
    boolean send(String mail, String code);

    boolean send(MsmVo msmVo);
}
