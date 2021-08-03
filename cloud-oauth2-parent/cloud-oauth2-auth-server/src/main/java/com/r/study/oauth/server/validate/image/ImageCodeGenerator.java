package com.r.study.oauth.server.validate.image;

import com.r.study.oauth.server.property.SecurityProperties;
import com.r.study.oauth.server.validate.BaseCode;
import com.r.study.oauth.server.validate.ValidateCodeGenerator;
import lombok.Data;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * date 2021-06-23 15:28
 *
 * @author HeYiHui
 **/
@Data
public class ImageCodeGenerator implements ValidateCodeGenerator {

    private SecurityProperties securityProperties;

    @Override
    public BaseCode generate(HttpServletRequest request) {
        // 长宽先从请求中取
        int width = ServletRequestUtils.getIntParameter(request, "width", securityProperties.getCode().getImageCode().getWidth());
        int height = ServletRequestUtils.getIntParameter(request, "height", securityProperties.getCode().getImageCode().getHeight());

        // 过期时间和长度不能通过请求指定
        int codeCount = securityProperties.getCode().getImageCode().getCodeCount();
        int expire = securityProperties.getCode().getImageCode().getExpireIn();
        return createImageCode(width, height, codeCount, expire);
    }

    private ImageCode createImageCode(int width, int height, int length, int expire) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        int fontSize = (int) (height * 0.7);
        g.setFont(new Font("Times New Roman", Font.ITALIC, fontSize));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, width / length * i, fontSize);
        }
        g.dispose();
        return new ImageCode(image, sRand.toString(), expire);
    }

    /**
     * 生成随机背景条纹
     *
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
