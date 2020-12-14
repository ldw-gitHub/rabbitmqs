package com.framemark.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @description   配置tomcat长链接
 * @author: liudawei
 * @date: 2020/11/19 17:24
 */
@Component
public class WebServerConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ((TomcatServletWebServerFactory) factory).addConnectorCustomizers(
                // 定制自己的tomcat容器
                new TomcatConnectorCustomizer() {
                    @Override
                    public void customize(Connector connector) {
                        //获取http协议对象 (Http11NioProtocol 是springboot 内置tomcat默认协议)
                        Http11NioProtocol http11NioProtocol = (Http11NioProtocol) connector.getProtocolHandler();
                        //定制keepalive
                        //如果30s之内，该连接处于空闲状态，释放这个链接
                        http11NioProtocol.setKeepAliveTimeout(30000);
                        //最大允许建立10000个长链接
                        http11NioProtocol.setMaxKeepAliveRequests(10000);
                    }
                }
        );
    }
}
