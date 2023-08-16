package com.example.superstoreclient.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    public CorsFilter corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*"); // 设置允许跨域请求的域名，这里设置为允许所有域名
        corsConfig.addAllowedMethod("*"); // 设置允许的请求方法，例如GET、POST
        corsConfig.addAllowedHeader("*"); // 设置允许的请求头，例如Content-Type

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsFilter(source);
    }

}