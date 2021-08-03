package com.r.study.oauth.server.authentication.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r.study.oauth.server.authentication.social.api.ApiBinding;
import com.r.study.oauth.server.authentication.social.entity.QQUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * date 2021-07-02 9:13
 *
 * @author HeYiHui
 **/
@Slf4j
public class QQImpl extends ApiBinding implements QQ {

    public static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    public static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId){
        super(accessToken);
        this.appId = appId;
        // 有了Appid 和 token之后去取openid
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = restTemplate.getForObject(url, String.class);

        log.info("QQ获取用户openid结果:{}", result);
        // 返回格式是 callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} ) 字符串所以需要截取一下
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        // 有了openid之后就可以获取用户信息了
        String url = String.format(URL_GET_USER_INFO, appId, openId);
        String result = restTemplate.getForObject(url, String.class);
        log.info("QQ获取用户信息结果:{}", result);

        // 转成对象返回
        QQUserInfo qqUserInfo = null;
        try {
            qqUserInfo = objectMapper.readValue(result, QQUserInfo.class);
            // 设置openid到用户信息里
            qqUserInfo.setOpenId(openId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取QQ用户信息失败");
        }

        return qqUserInfo;
    }
}
