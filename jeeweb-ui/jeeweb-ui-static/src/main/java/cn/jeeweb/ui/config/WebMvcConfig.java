package cn.jeeweb.ui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置静态资源映射
 * @author 王存见
 * @since 2018-08-09
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

//        registry.addResourceHandler("/**").addResourceLocations("file:/usr/local/jeeweb/img/");
        registry.addResourceHandler("/**").addResourceLocations("file:/usr/local/outerWeb/img/");

    }
}