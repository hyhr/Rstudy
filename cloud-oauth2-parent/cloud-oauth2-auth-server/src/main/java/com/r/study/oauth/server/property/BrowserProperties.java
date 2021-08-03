package com.r.study.oauth.server.property;

import com.r.study.oauth.server.constant.Constants;
import com.r.study.oauth.server.enums.LoginResponseType;
import lombok.Data;

/**
 * date 2021-06-23 9:20
 *
 * @author HeYiHui
 **/
@Data
public class BrowserProperties {

    /**
     * 登录页
     */
    private String loginPage = Constants.DEFAULT_LOGIN_PAGE_URL;

    /**
     * 登录返回类型
     */
    private LoginResponseType loginType = LoginResponseType.JSON;

    /**
     * 记住我秒数
     */
    private int rememberMeSeconds = 60 * 60 * 24;

}
