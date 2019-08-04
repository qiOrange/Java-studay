package com.github.shiro.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

	@RequestMapping("hello")
	public String hello() {
		return "TestController.hello()";
	}
	
	
	
}
