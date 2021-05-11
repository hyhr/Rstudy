package com.r.study.monkey.sign.algorithm;

import java.util.Map;

public interface SignAlgorithm {

	/**
	 * 签名
	 * @param param 签名内容
	 * @param key 签名Key
	 * @return 签名内容
	 * @throws Exception 签名失败
	 */
	String sign(Map<String, Object> param, String key) throws Exception;

	/**
	 * 验签
	 * @param param 验签参数Map
	 * @param key 验签Key
	 * @return 验签内容
	 * @throws Exception 验签失败
	 */
	boolean checkSign(Map<String, Object> param, String key) throws Exception;

}
