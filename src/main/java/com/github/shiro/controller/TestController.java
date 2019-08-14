package com.github.shiro.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 控制器层
 * @author 小七
 *
 */
@Controller
public class TestController {
	@RequestMapping("/red")
	public String red() {
		return "grap";
	}
	
	@RequestMapping("index")
	public String index(Model model) {
		model.addAttribute("themleaf", "欢迎进入主页面 !");
		return "index";
	}
	
	@RequestMapping("/add")
	public String add() {
		return "/user/adduser";
	}
	@RequestMapping("/update")
	public String update() {
		return "/user/updateuser";
	}
	@RequestMapping("/upload")
	public String unload() {
		return "upload";
	}
	@RequestMapping("/tologin")
	public String toLogin() {
		return "login";
	}
	@RequestMapping("/login")
	public String login(@RequestParam("name")String name,@RequestParam("password")String password,Model model) {
		/**
		 * 获取当前用户对象
		 */
		Subject subject=SecurityUtils.getSubject();
		/**
		 * 将获取的值放入token 
		 */
		UsernamePasswordToken token=new UsernamePasswordToken(name,password);
		try {
			/**
			 * 认证当前用户是否为登陆用户 ，进行验证Realm 逻辑验证
			 * 如果验证成功则会返回指定的页面，并且留下一个cookie，cookie时长也可通过配置文件或者配置类进行设置
			 *  下次将不会验证用户，用户获取基本登陆权限
			 *  UserRealm 中的 验证方法
			 */
			subject.login(token);
			System.out.println("登陆成功");
			/**
			 * 登陆成功将会重定向到业务界面
			 */
			return "redirect:/index";
		} catch (UnknownAccountException e) {
			/**
			 * 这里会抛出一个用户不存在的异常 返回给前端一个信息告之业务处理失败
			 */
			model.addAttribute("msg", "用户不存在");
			//将页面跳转到login页面
			return "login";
		}catch (IncorrectCredentialsException e) {
			/**
			 * 这里会抛出一个错误凭证的异常 返回给前端一个信息告之业务处理失败
			 */
			model.addAttribute("msg", "密码错误");
			//将页面跳转到login页面
			return "login";
		}
		
	}
	/**
	 * 跳转一个未授权页面
	 * @return
	 */
	@RequestMapping("/unAuth")
	public String unAuth() {
		return "noAuth";
	}
	
}
