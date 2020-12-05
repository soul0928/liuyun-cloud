package com.liuyun.log.logback;


import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取IP
 * @author wangdong
 * @date 2020/7/31 8:26 下午
 **/
public class IpConverter extends ClassicConverter {
    
    @Override
    public String convert(ILoggingEvent arg0) {
        String ip = "";
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            // 获得本机IP
            ip = addr.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            ip = "";
        }
        return ip;
    }

}
