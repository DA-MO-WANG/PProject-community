package com.newcoder.community.config;

import com.newcoder.community.interceptor.DataInterceptor;
import com.newcoder.community.interceptor.LoginRequiredInterceptor;
import com.newcoder.community.interceptor.LoginTicketInterceptor;
import com.newcoder.community.interceptor.MessageInterceptor;
import com.newcoder.community.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;
   /* @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;*/
    @Autowired
    private MessageInterceptor messageInterceptor;
    @Autowired
    private DataInterceptor dataInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.css","/**/*.js","/**/*.png","/**/*.jpg","/**/*.jpeg");
        //registry.addInterceptor(loginRequiredInterceptor)
                //.excludePathPatterns("/**/*.css","/**/*.css","/**/*.js","/**/*.png","/**/*.jpg","/**/*.jpeg");
        registry.addInterceptor(messageInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.css","/**/*.js","/**/*.png","/**/*.jpg","/**/*.jpeg");
        registry.addInterceptor(dataInterceptor)
                .excludePathPatterns("/**/*.css","/**/*.css","/**/*.js","/**/*.png","/**/*.jpg","/**/*.jpeg");
    }
}
