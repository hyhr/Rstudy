package com.r.study.monkey.sign.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.r.study.monkey.sign.springboot.init.ApiSignDataInit;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.util.CollectionUtils;

/**
 * 签名配置类
 *
 * @author yinjihuan
 *
 */
@ConfigurationProperties(prefix = "spring.sign")
public class SignConfig {

	/**
	 * 通讯密码
	 */
	private String key = "d7b85f6e214abcda";

	/**
	 * 需要对响应内容进行签名的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> responseSignUriList = new ArrayList<String>();

	/**
	 * 需要对请求内容进行验签的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> requestCheckSignUriList = new ArrayList<String>();

	/**
	 * 忽略签名的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> responseSignUriIgnoreList = new ArrayList<String>();

	/**
	 * 忽略对请求内容进行验签的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	private List<String> requestCheckSignUriIgnoreList = new ArrayList<String>();

	/**
	 * 响应数据编码
	 */
	private String responseCharset = "UTF-8";

	/**
	 * 开启调试模式，调试模式下不进行签名操作，用于像Swagger这种在线API测试场景
	 */
	private boolean debug = false;

	/**
	 * 过滤器拦截模式
	 */
	private String[] urlPatterns = new String[] { "/*" };

	/**
	 * 过滤器执行顺序
	 */
	private int order = 1;

	public SignConfig() {
		super();
	}

	public SignConfig(String key, List<String> responseSignUriList, List<String> requestCheckSignUriList,
					  String responseCharset, boolean debug) {
		super();
		this.key = key;
		this.responseSignUriList = responseSignUriList;
		this.requestCheckSignUriList = requestCheckSignUriList;
		this.responseCharset = responseCharset;
		this.debug = debug;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getResponseSignUriList() {
		return Stream.of(responseSignUriList, ApiSignDataInit.responseSignUriList).flatMap(Collection::stream).collect(Collectors.toList());
	}

	public void setResponseSignUriList(List<String> responseSignUriList) {
		this.responseSignUriList = responseSignUriList;
	}

	public List<String> getRequestCheckSignUriList() {
		return Stream.of(requestCheckSignUriList, ApiSignDataInit.requestCheckSignUriList).flatMap(Collection::stream).collect(Collectors.toList());
	}

	public void setRequestCheckSignUriList(List<String> requestCheckSignUriList) {
		this.requestCheckSignUriList = requestCheckSignUriList;
	}

	public String getResponseCharset() {
		return responseCharset;
	}

	public void setResponseCharset(String responseCharset) {
		this.responseCharset = responseCharset;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setUrlPatterns(String[] urlPatterns) {
		this.urlPatterns = urlPatterns;
	}

	public String[] getUrlPatterns() {
		return urlPatterns;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public List<String> getResponseSignUriIgnoreList() {
		// 配置和注解两种方式合并
		return Stream.of(responseSignUriIgnoreList, ApiSignDataInit.responseSignUriIgnoreList).flatMap(Collection::stream).collect(Collectors.toList());
	}

	public void setResponseSignUriIgnoreList(List<String> responseSignUriIgnoreList) {
		this.responseSignUriIgnoreList = responseSignUriIgnoreList;
	}

	public List<String> getRequestCheckSignUriIgnoreList() {
		// 配置和注解两种方式合并
		return Stream.of(requestCheckSignUriIgnoreList, ApiSignDataInit.requestCheckSignUriIgnoreList).flatMap(Collection::stream).collect(Collectors.toList());
	}

	public void setRequestCheckSignUriIgnoreList(List<String> requestDecyptUriIgnoreList) {
		this.requestCheckSignUriIgnoreList = requestDecyptUriIgnoreList;
	}

	public List<String> getRequestCheckSignParams(String uri) {
		List<String> params = ApiSignDataInit.requestCheckSignParamMap.get(uri);
		if (CollectionUtils.isEmpty(params)) {
			return new ArrayList<>();
		}

		return params;
	}


}
