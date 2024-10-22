package edu.wgu.d387_sample_code.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Apply CORS settings to all paths
                .allowedOrigins("http://localhost:4200")  // Allow requests from Angular frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow specified HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(true);  // Allow credentials (cookies, authorization headers)
    }
}
