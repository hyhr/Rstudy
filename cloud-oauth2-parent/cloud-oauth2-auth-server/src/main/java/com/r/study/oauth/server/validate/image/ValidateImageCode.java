package com.r.study.oauth.server.validate.image;

import lombok.Data;

import java.awt.image.BufferedImage;

/**
 * date 2021-06-23 11:07
 *
 * @author HeYiHui
 **/
@Data
public class ValidateImageCode {

    private String code;

    private BufferedImage buffImg;
}
