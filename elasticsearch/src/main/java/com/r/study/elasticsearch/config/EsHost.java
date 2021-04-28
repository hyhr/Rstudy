package com.r.study.elasticsearch.config;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * es 节点
 * date 2021-04-27 18:28
 *
 * @author HeYiHui
 **/
public class EsHost implements Serializable {

    private String hostname;

    private int port;

    private String schemeName;

    private InetAddress address;

    public EsHost() {}

    public EsHost(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        this.schemeName = "http";
    }

    public EsHost(String hostname, int port, String schemeName) {
        this.hostname = hostname;
        this.port = port;
        this.schemeName = schemeName;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }
}
