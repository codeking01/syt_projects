package com.codeking.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codeking.yygh.model.user.UserInfo;
import com.codeking.yygh.vo.user.LoginVo;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {

    //会员登录
    Map<String, Object> login(LoginVo loginVo);

    /**
     * 根据微信openid获取用户信息
     *
     * @param openid
     * @return
     */
    UserInfo getByOpenid(String openid);
}
