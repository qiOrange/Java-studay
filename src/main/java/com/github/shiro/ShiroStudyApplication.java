package com.github.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.github.shiro.mapper")
@SpringBootApplication
public class ShiroStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiroStudyApplication.class, args);
	}

}
