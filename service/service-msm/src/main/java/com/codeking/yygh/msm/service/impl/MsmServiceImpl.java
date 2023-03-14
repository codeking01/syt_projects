package com.codeking.yygh.msm.service.impl;

import com.codeking.yygh.msm.config.ConstantPropertiesUtils;
import com.codeking.yygh.msm.service.MsmService;
import com.codeking.yygh.msm.utils.mailUtil;
import com.codeking.yygh.vo.msm.MsmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MsmServiceImpl implements MsmService {
    // 注入发送邮件的
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean send(String mail, String code) {
        //判断手机号是否为空
        if (StringUtils.isEmpty(mail)) {
            return false;
        }
        // 整合邮件服务
        String from = ConstantPropertiesUtils.USERNAME;
        String subject = "尚医通登陆验证码";
        try {
            mailUtil.sendSimpleMail(javaMailSender, from, mail, subject, code);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean send(MsmVo msmVo) {
        if (!StringUtils.isEmpty(msmVo.getPhone())) {
            String code = (String) msmVo.getParam().get("code");
            return this.send(msmVo.getPhone(), code);
        }
        return false;
    }

}
