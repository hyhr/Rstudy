package com.r.study.monkey.sign.springboot.init;

import java.lang.reflect.Method;
import java.util.*;

import com.r.study.monkey.sign.springboot.HttpMethodTypePrefixConstant;
import com.r.study.monkey.sign.springboot.annotation.Sign;
import com.r.study.monkey.sign.springboot.annotation.SignIgnore;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.r.study.monkey.sign.springboot.annotation.CheckSign;
import com.r.study.monkey.sign.springboot.annotation.CheckSignIgnore;

@Slf4j
public class ApiSignDataInit implements ApplicationContextAware {

	/**
	 * 需要对响应内容进行签名的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	public static List<String> responseSignUriList = new ArrayList<>();

	/**
	 * 需要对请求内容进行验签的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	public static List<String> requestCheckSignUriList = new ArrayList<>();

	/**
	 * 忽略签名的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	public static List<String> responseSignUriIgnoreList = new ArrayList<>();

	/**
	 * 忽略对请求内容进行验签的接口URI<br>
	 * 比如：/user/list<br>
	 * 不支持@PathVariable格式的URI
	 */
	public static List<String> requestCheckSignUriIgnoreList = new ArrayList<String>();

	/**
	 * Url参数需要验签的配置
	 * 比如：/user/list?name=加密内容<br>
	 * 格式：Key API路径  Value 需要验签的字段
	 * 示列：/user/list  [name,age]
	 */
	public static Map<String, List<String>> requestCheckSignParamMap = new HashMap<>();

	private String contextPath;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
    	this.contextPath = ctx.getEnvironment().getProperty("server.servlet.context-path");
		Map<String, Object> beanMap = ctx.getBeansWithAnnotation(Controller.class);
        initData(beanMap);
        initRequestCheckSignParam(ctx.getEnvironment());
    }

	/**
	 * 初始化Url 参数验签配置
	 * @param environment
	 */
	private void initRequestCheckSignParam(Environment environment) {
		for(Iterator it = ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext(); ) {
			PropertySource propertySource = (PropertySource) it.next();
			if (propertySource instanceof EnumerablePropertySource) {
				for(String name : ((EnumerablePropertySource)propertySource).getPropertyNames()) {
					if (name.startsWith("spring.sign.requestCheckSignParam")) {
						String[] keys = name.split("\\.");
						String key = keys[keys.length - 1];
						String property = environment.getProperty(name);
						requestCheckSignParamMap.put(key.replace("$", ":"), Arrays.asList(property.split(",")));
					}
				}
			}
		}
	}

	private void initData(Map<String, Object> beanMap) {
		if (beanMap != null) {
            for (Object bean : beanMap.values()) {
                Class<?> clz = bean.getClass();
                Method[] methods = clz.getMethods();
                for (Method method : methods) {
                	Sign sign = AnnotationUtils.findAnnotation(method, Sign.class);
                	if (sign != null) {
                		// 注解中的URI优先级高
                    	String uri = sign.value();
                    	if (!StringUtils.hasText(uri)) {
                    		uri = getApiUri(clz, method);
						}
                        log.debug("sign URI: {}", uri);
                        responseSignUriList.add(uri);
                	}
                	CheckSign checkSign = AnnotationUtils.findAnnotation(method, CheckSign.class);
                    if (checkSign != null) {
                    	String uri = checkSign.value();
                    	if (!StringUtils.hasText(uri)) {
                    		uri = getApiUri(clz, method);
						}

                    	String checkSignParam = checkSign.checkSignParam();
						if (StringUtils.hasText(checkSignParam)) {
							requestCheckSignParamMap.put(uri, Arrays.asList(checkSignParam.split(",")));
						}

                        log.debug("check sign URI: {}", uri);
                        requestCheckSignUriList.add(uri);
                    }
                    SignIgnore signIgnore = AnnotationUtils.findAnnotation(method, SignIgnore.class);
                	if (signIgnore != null) {
                		// 注解中的URI优先级高
                    	String uri = signIgnore.value();
                    	if (!StringUtils.hasText(uri)) {
                    		uri = getApiUri(clz, method);
						}
                        log.debug("SignIgnore URI: {}", uri);
                        responseSignUriIgnoreList.add(uri);
                	}
                	CheckSignIgnore checkSignIgnore = AnnotationUtils.findAnnotation(method, CheckSignIgnore.class);
                    if (checkSignIgnore != null) {
                    	String uri = checkSignIgnore.value();
                    	if (!StringUtils.hasText(uri)) {
                    		uri = getApiUri(clz, method);
						}
                        log.debug("CheckSignIgnore URI: {}", uri);
                        requestCheckSignUriIgnoreList.add(uri);
                    }
                }
            }
        }
	}

    private String getApiUri(Class<?> clz, Method method) {
    	String methodType = "";
        StringBuilder uri = new StringBuilder();

        RequestMapping reqMapping = AnnotationUtils.findAnnotation(clz, RequestMapping.class);
        if (reqMapping != null) {
        	uri.append(formatUri(reqMapping.value()[0]));
		}

        GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
        PostMapping postMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        PutMapping putMapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
        DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);

        if (getMapping != null) {
        	methodType = HttpMethodTypePrefixConstant.GET;
            uri.append(formatUri(getMapping.value()[0]));

        } else if (postMapping != null) {
        	methodType = HttpMethodTypePrefixConstant.POST;
            uri.append(formatUri(postMapping.value()[0]));

        } else if (putMapping != null) {
        	methodType = HttpMethodTypePrefixConstant.PUT;
            uri.append(formatUri(putMapping.value()[0]));

        } else if (deleteMapping != null) {
        	methodType = HttpMethodTypePrefixConstant.DELETE;
            uri.append(formatUri(deleteMapping.value()[0]));

        } else if (requestMapping != null) {
			RequestMethod requestMethod = RequestMethod.GET;
        	if (requestMapping.method().length > 0) {
				requestMethod = requestMapping.method()[0];
			}

        	methodType = requestMethod.name().toLowerCase() + ":";
            uri.append(formatUri(requestMapping.value()[0]));

        }

        if (StringUtils.hasText(this.contextPath) && !"/".equals(this.contextPath)) {
        	if (this.contextPath.endsWith("/")) {
        		this.contextPath = this.contextPath.substring(0, this.contextPath.length() - 1);
			}
        	 return methodType + this.contextPath + uri.toString();
		}

        return methodType + uri.toString();
}

    private String formatUri(String uri) {
    	if (uri.startsWith("/")) {
			return uri;
		}
    	return "/" + uri;
    }
}
