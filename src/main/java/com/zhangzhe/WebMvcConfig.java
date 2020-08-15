package com.zhangzhe;

import com.zhangzhe.handler.LoginCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean(initMethod="init")
	public ZKCuratorClient zkCuratorClient() {
		return new ZKCuratorClient();
	}

	@Bean
	public LoginCheckInterceptor loginCheckInterceptor(){return new LoginCheckInterceptor();}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
		.addResourceLocations("classpath:/META-INF/resources/")
				.addResourceLocations("file:C:/imooc_videos_dev/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor()).addPathPatterns("/user/**")
				.addPathPatterns("/video/upload", "/video/uploadCover",
						"/video/userLike", "/video/userUnLike",
						"/video/saveComment")
				.addPathPatterns("/bgm/**")
				.excludePathPatterns("/user/queryPublisher");
		super.addInterceptors(registry);
	}
}
