package com.example.behind.config;

import com.example.behind.Interceptor.AdminInterceptor;
import com.example.behind.Interceptor.JwtInterceptor;
import com.example.behind.Interceptor.LoginInterceptor;
import com.example.behind.Interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/**");

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/auth/**");

        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**", "/admin");

        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/user/**", "/user");


    }

}
