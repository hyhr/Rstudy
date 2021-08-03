package com.r.study.oauth.server.validate.image;

import com.r.study.oauth.server.validate.property.BaseCodeProperties;
import lombok.Data;

/**
 * date 2021-06-23 14:33
 *
 * @author HeYiHui
 **/
@Data
public class ImageCodeProperties extends BaseCodeProperties {

    /**
     * 图片的宽度
     */
    private int width = 160;

    /**
     * 图片的高度
     */
    private int height = 100;

}
