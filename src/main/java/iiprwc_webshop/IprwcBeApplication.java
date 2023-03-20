package iiprwc_webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class IprwcBeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(IprwcBeApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200", "http://iprwc-fe.s3-website-us-east-1.amazonaws.com").allowedMethods("PUT", "DELETE", "PATCH", "POST", "GET");
            }
        };
    }
}
