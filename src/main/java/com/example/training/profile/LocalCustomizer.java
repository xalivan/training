//package com.example.training.profile;
//import org.springframework.boot.web.server.WebServerFactoryCustomizer;
//import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@Profile("local")
//public class LocalCustomizer  implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
//    @Override
//    public void customize(ConfigurableServletWebServerFactory factory) {
//        factory.setContextPath("/postgres");
//        factory.setPort(8081);
//    }
//}
