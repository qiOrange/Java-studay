package com.github.shiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Component
public class Beans {
	@Bean("multipartResolver")
	public CommonsMultipartResolver  getComm() {
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1048576*10);
		multipartResolver.setMaxUploadSizePerFile(1048576);
		return multipartResolver;
	}
}
