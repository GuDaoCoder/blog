package com.blog.common.initialize;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zouzhangpeng
 * @desc 项目初始化完成后做一些事
 */
@Slf4j
@Component
public class ProjectInitialize {

    @Value("${server.port}")
    private int port;

    @PostConstruct
    public void initialize() throws UnknownHostException {
        // todo: 只有开发环境才输出
        String address =
            Optional.ofNullable(InetAddress.getLocalHost()).map(InetAddress::getHostAddress).orElse("127.0.0.1");
        log.info("druid url: http://{}:{}/druid/login.html", address, port);
        log.info("knife4j url: http://{}:{}/doc.html", address, port);
    }
}
