package com.r.study.oauth.server.validate.image;

import com.r.study.oauth.server.validate.BaseCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * date 2021-06-23 11:03
 *
 * @author HeYiHui
 **/
@Data
public class ImageCode extends BaseCode {

    /**
     * 图片对象
     * transient 序列化的时候这个字段不会被序列化
     */
    private transient BufferedImage image;

    /**
     * 构造
     * @param image 图片对象
     * @param code 验证码
     * @param expireIn 过期秒数
     */
    public ImageCode (BufferedImage image, String code, int expireIn){
        super(code, LocalDateTime.now().plusSeconds(expireIn));
        this.image = image;
    }
}
