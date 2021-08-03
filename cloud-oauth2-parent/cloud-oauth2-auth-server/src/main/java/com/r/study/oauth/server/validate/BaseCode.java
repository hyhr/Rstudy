package com.r.study.oauth.server.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * date 2021-06-23 16:55
 *
 * @author HeYiHui
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCode implements Serializable {

    /**
     * 验证码
     */
    private String code;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 验证码是否过期
     * @return
     */
    public boolean expired(){
        return LocalDateTime.now().isAfter(expireTime);
    }

}
