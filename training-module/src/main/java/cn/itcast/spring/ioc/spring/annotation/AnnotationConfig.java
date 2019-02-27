package cn.itcast.spring.ioc.spring.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AnnotationConfig {
	@Bean(name="user")
	public User getUser(){
		return new User();
	}
}
