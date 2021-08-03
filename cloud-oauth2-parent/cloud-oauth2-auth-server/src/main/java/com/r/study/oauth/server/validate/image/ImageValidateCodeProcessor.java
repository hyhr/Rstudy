package com.r.study.oauth.server.validate.image;

import com.r.study.oauth.server.validate.processor.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * date 2021-06-23 17:23
 *
 * @author HeYiHui
 **/
@Component
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(HttpServletRequest request, HttpServletResponse response, ImageCode imageCode) throws IOException {
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

}
