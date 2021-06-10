package com.r.study.monkey.sign.core;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.r.study.monkey.sign.exception.SignException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.r.study.monkey.sign.algorithm.Md5SignAlgorithm;
import com.r.study.monkey.sign.algorithm.SignAlgorithm;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 数据签名过滤器
 *
 * @author yinjihuan
 */
@Slf4j
public class SignFilter implements Filter {

    private SignConfig signConfig;

    private SignAlgorithm signAlgorithm = new Md5SignAlgorithm();

    public SignFilter() {
        this.signConfig = new SignConfig();
    }

    public SignFilter(SignConfig config) {
        this.signConfig = config;
    }

    public SignFilter(SignConfig config, SignAlgorithm signAlgorithm) {
        this.signConfig = config;
        this.signAlgorithm = signAlgorithm;
    }

    public SignFilter(String key) {
        SignConfig config = new SignConfig();
        config.setKey(key);
        this.signConfig = config;
    }

    public SignFilter(String key, List<String> responseSignUriList, List<String> requestCheckSignUriList,
                      String responseCharset, boolean debug) {
        this.signConfig = new SignConfig(key, responseSignUriList, requestCheckSignUriList, responseCharset, debug);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        log.debug("RequestURI: {}", uri);

        // 调试模式不签名
        if (signConfig.isDebug()) {
            chain.doFilter(req, resp);
            return;
        }

        boolean checkSignStatus = this.contains(signConfig.getRequestCheckSignUriList(), uri, req.getMethod());
        boolean signStatus = this.contains(signConfig.getResponseSignUriList(), uri, req.getMethod());
        boolean checkSignIgnoreStatus = this.contains(signConfig.getRequestCheckSignUriIgnoreList(), uri, req.getMethod());
        boolean signIgnoreStatus = this.contains(signConfig.getResponseSignUriIgnoreList(), uri, req.getMethod());

        // 没有配置具体签名的URI默认全部都开启签名
        if (CollectionUtils.isEmpty(signConfig.getRequestCheckSignUriList())
                && CollectionUtils.isEmpty(signConfig.getResponseSignUriList())) {
//            checkSignStatus = true;
            signStatus = true;
        }

        // 接口在忽略加密列表中
        if (signIgnoreStatus) {
            signStatus = false;
        }

        // 接口在忽略验签列表中
        if (checkSignIgnoreStatus) {
            checkSignStatus = false;
        }

        // 没有签名操作
        if (!checkSignStatus && !signStatus) {
            chain.doFilter(req, resp);
            return;
        }

        SignResponseWrapper responseWrapper = null;
        SignReqestWrapper requestWrapper = null;
        // 配置了需要验签才处理
        if (checkSignStatus) {
            requestWrapper = new SignReqestWrapper(req);
            processCheckSign(requestWrapper, req);
        }

        if (signStatus) {
            responseWrapper = new SignResponseWrapper(resp);
        }

        // 同时需要加解密
        if (signStatus && checkSignStatus) {
            chain.doFilter(requestWrapper, responseWrapper);
        } else if (signStatus) {
            // 只需要响应加密
            chain.doFilter(req, responseWrapper);
        } else if (checkSignStatus) {
            // 只需要请求解密
            chain.doFilter(requestWrapper, resp);
        }
        // 配置了需要签名才处理
        if (signStatus) {
            String responseData = responseWrapper.getResponseData();
            writeSignContent(responseData, response);

        }

    }

    /**
     * 请求验签处理
     *
     * @param requestWrapper
     * @param req
     */
    private void processCheckSign(SignReqestWrapper requestWrapper, HttpServletRequest req) {
        String requestData = requestWrapper.getRequestData();
        String uri = req.getRequestURI();
        log.debug("RequestData: {}", requestData);
        try {
            Map<String, Object> requestParamMap = null;
            if (!StringUtils.isEmpty(requestData)) {
                requestParamMap = JSON.parseObject(requestData, new TypeReference<Map<String, Object>>() {
                });
            }
            if (!StringUtils.endsWithIgnoreCase(req.getMethod(), RequestMethod.GET.name())) {
                if (requestParamMap != null && requestParamMap.size() > 0) {
                    boolean checkSignRequestData = signAlgorithm.checkSign(requestParamMap, signConfig.getKey());
                    log.debug("checkSignRequestData: {}", checkSignRequestData);
                    String sign = (String) requestParamMap.get("sign");
                    if (StringUtils.isEmpty(sign)) {
                        //缺少必要的sign字段
                        throw new SignException("参数缺少必要的sign字段");
                    }
                    if (!checkSignRequestData) {
                        //验签不通过
                        throw new SignException("验签不通过");
                    }
                }
//                requestWrapper.setRequestData(checkSignRequestData);
            }
            // url参数验签
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
                /*String prefixUri = req.getMethod().toLowerCase() + ":" + uri;
                if (signConfig.getRequestCheckSignParams(prefixUri).contains(paramName)) {
                }*/
            }
            if (paramMap.size() > 0) {
                boolean checkSignParamValue = signAlgorithm.checkSign(paramMap, signConfig.getKey());
                log.debug("checkSignRequestData: {}", checkSignParamValue);
                String sign = (String) paramMap.get("sign");
                if (StringUtils.isEmpty(sign)) {
                    //缺少必要的sign字段
                    throw new SignException("参数缺少必要的sign字段");
                }
                if (!checkSignParamValue) {
                    //验签不通过
                    throw new SignException("验签不通过");
                }
            }
        } catch (Exception e) {
            log.error("请求数据验签失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 输出签名内容
     *
     * @param responseData
     * @param response
     * @throws IOException
     */
    private void writeSignContent(String responseData, ServletResponse response) throws IOException {
        log.debug("ResponseData: {}", responseData);
        ServletOutputStream out = null;
        try {
            Map<String, Object> responseDataMap = null;
            if (!StringUtils.isEmpty(responseData)) {
                responseDataMap = JSON.parseObject(responseData, new TypeReference<Map<String, Object>>() {
                });
            }
            if (responseDataMap != null && responseDataMap.size() > 0) {
                String sign = signAlgorithm.sign(responseDataMap, signConfig.getKey());
                responseDataMap.put("sign", sign);
                responseData = JSON.toJSONString(responseDataMap, SerializerFeature.WriteMapNullValue);
                log.debug("signResponseData: {}", responseData);
                //TODO 长度不对，先注释了
//                response.setContentLength(responseData.length());
                response.setCharacterEncoding(signConfig.getResponseCharset());
                out = response.getOutputStream();
                out.write(responseData.getBytes(signConfig.getResponseCharset()));
            }
        } catch (Exception e) {
            log.error("响应数据签名失败", e);
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    private boolean contains(List<String> list, String uri, String methodType) {
        if (list.contains(uri)) {
            return true;
        }
        String prefixUri = methodType.toLowerCase() + ":" + uri;
        log.debug("contains uri: {}", prefixUri);
        if (list.contains(prefixUri)) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {}
}
