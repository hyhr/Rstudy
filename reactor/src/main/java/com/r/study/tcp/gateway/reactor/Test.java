package com.r.study.tcp.gateway.reactor;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.asprise.ocr.Ocr;
import com.asprise.ocr.util.StringUtils;

import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * date 2021-09-15 13:49
 *
 * @author HeYiHui
 **/
public class Test {

    public static void main(String[] args) {

        String method = " kssjbd";
        String lxsj = "15015512505";
        String sjyzm = "461225";
        String yzbj = "1";
        String verifyCodeImg = "wwww";
        String cookie = "BIGipServercr_student_pool=1053294602.16671.0000; JSESSIONID=JfHn9FNPI49T7n7YVeihvDvxhb34aymavKKA32sqdvFUnwo0ESgN!-1786361511!2106504788";
        String verifyUrl = "https://www.eeagd.edu.cn/cr/servlet/VerifyCodeServlet";
        String url = "https://www.eeagd.edu.cn/cr/kssjbdServlet";

        HttpResponse execute = HttpRequest.get(verifyUrl).header("Cookie", cookie).execute();
        File dest = new File("D:\\tmp\\tmp.jpg");
        FileUtil.writeFromStream(execute.bodyStream(), dest);
        try {
            verifyCodeImg = new OCRHelper().recognizeText(dest);
            System.out.println(verifyCodeImg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!"1".equals(verifyCodeImg)) {
                HttpResponse response = HttpRequest.post(url)
                        .header("Cookie", cookie)
                        .header("Host", "www.eeagd.edu.cn")
                        .form("method", method)
                        .form("lxsj", lxsj)
                        .form("sjyzm", sjyzm)
                        .form("yzbj", yzbj)
                        .form("verifyCodeImg", verifyCodeImg)
                        .execute();
                System.out.println(response);
            }
    }
}
