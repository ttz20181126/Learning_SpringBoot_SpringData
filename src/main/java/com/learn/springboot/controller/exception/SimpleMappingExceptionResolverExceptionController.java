package com.learn.springboot.controller.exception;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
public class SimpleMappingExceptionResolverExceptionController{

    /**
     * 访问 http://localhost:8080/defaultExceptionPage3
     * 访问 http://localhost:8080/defaultExceptionPage4
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver smer = new SimpleMappingExceptionResolver();

        Properties mappings = new Properties();
        mappings.put("java.lang.NullPointerException","error_conf_null");
        mappings.put("java.lang.ArithmeticException","error_conf_arithmetic");
        smer.setExceptionMappings(mappings);
        return smer;
    }

}