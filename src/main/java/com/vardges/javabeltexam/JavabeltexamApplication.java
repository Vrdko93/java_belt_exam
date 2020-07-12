package com.vardges.javabeltexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AjpNioProtocol;

@SpringBootApplication
public class JavabeltexamApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavabeltexamApplication.class, args);
	}

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        Connector ajpConnector = new Connector("AJP/1.3");
        ajpConnector.setPort(9090);
        ajpConnector.setSecure(false);
        ajpConnector.setAllowTrace(false);
        ajpConnector.setScheme("http");
        tomcat.addAdditionalTomcatConnectors(ajpConnector);
        AjpNioProtocol protocol= (AjpNioProtocol)ajpConnector.getProtocolHandler();
        protocol.setSecretRequired(false);
        return tomcat;
    }
}
