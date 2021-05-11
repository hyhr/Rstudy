package com.r.study.monkey.sign.algorithm;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * MD5签名算法实现
 *
 */
public class Md5SignAlgorithm implements SignAlgorithm {

	@Override
	public String sign(Map<String, Object> param, String signKey) throws Exception {
		Map<String, Object> signDataMap = new LinkedHashMap<>(param.size());
        signDataMap.putAll(param);
        //去除sign和data参数
        signDataMap.remove("sign");
        signDataMap.remove("data");
        Set<String> keySet = signDataMap.keySet();
        String contentStr = "";
        for (String key : keySet) {
            if (StringUtils.isEmpty(contentStr)) {
                contentStr = key + "=" + signDataMap.get(key);
            } else {
                contentStr = contentStr + "&" + key + "=" + signDataMap.get(key);
            }
        }
        if (!StringUtils.isEmpty(contentStr)) {
            contentStr = contentStr + "&" + signKey;
        }
        String md5 = DigestUtils.md5DigestAsHex(contentStr.getBytes());
        return md5.toLowerCase();
	}

	@Override
	public boolean checkSign(Map<String, Object> param, String checkSignKey) throws Exception {
		String reqSign = (String) param.get("sign");
        String sign = sign(param, checkSignKey);
        return reqSign != null && reqSign.equals(sign);
	}
}
