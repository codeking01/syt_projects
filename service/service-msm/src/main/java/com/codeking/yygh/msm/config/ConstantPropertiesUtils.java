package com.codeking.yygh.msm.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 实现InitializingBean，会读取配置文件信息
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${spring.mail.username}")
    private String userName;

    public static String USERNAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        USERNAME=userName;
    }
}

/*
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${aliyun.sms.regionId}")
    private String regionId;

    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.secret}")
    private String secret;

    public static String REGION_Id;
    public static String ACCESS_KEY_ID;
    public static String SECRECT;

    @Override
    public void afterPropertiesSet() throws Exception {
        REGION_Id=regionId;
        ACCESS_KEY_ID=accessKeyId;
        SECRECT=secret;
    }
}
*/
