package com.r.study.oauth.server.authentication.social;

import com.r.study.oauth.server.authentication.social.entity.QQUserInfo;

import java.io.IOException;

/**
 * date 2021-07-02 9:08
 *
 * @author HeYiHui
 **/
public interface QQ {

    /**
     * 获取用户信息
     * @return {@link QQUserInfo}
     */
    QQUserInfo getUserInfo() throws IOException;


}
