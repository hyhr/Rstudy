package com.r.study.tcp.gateway.tcp.message;
import java.io.Serializable;

/**
 * 消息系统信息
 * @author YiHui.He
 */
public class SystemMessage implements Serializable {

    /**
     * 远程地址
     */
    private String remoteAddress;
    /**
     * 本地 地址
     */
    private String localAddress;

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }
}
