package com.vikingsrise.simulator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static files from the /static directory
        registry.addResourceHandler("/**")
                .addResourceLocations("file:static/", "classpath:/static/")
                .setCachePeriod(3600);
    }
}
