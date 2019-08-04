package com.github.shiro.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
/**
 * shiro配置类
 * @author 小七
 *
 */
@Configuration
public class shiroConfig {
	/**
	 * shiro 核心方法设置拦截器规则
	 * @param defaultWebSecurityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager) {
		/**
		 * 设置一个shiroFilterFactoryBean 对象
		 */
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		/**
		 * 设置默认的安全管理级别到该对象中
		 */
		shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
		/**
		 * 添加shiro 内置过滤器
		 * 设置拦截规则
		 * 常用过滤器:anon 不拦截
		 * perms 	user:add 关键词不拦截
		 * authc 拦截返回 setLoginUrl("/tologin");
		 */
		Map<String, String>filterMap=new LinkedHashMap<>();
		filterMap.put("/themleaf", "anon");
		filterMap.put("/login", "anon");
		filterMap.put("/add", "perms[user:add]");
		filterMap.put("/update", "perms[user:update]");
		filterMap.put("/*", "authc");
		/**
		 * 不设置默认login.jsp
		 * shiroFilterFactoryBean.setLoginUrl("/tologin")
		 */
		shiroFilterFactoryBean.setLoginUrl("/tologin");	
		shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");
		/**
		 * 将LinkedHashmap 中的值按顺序拦截
		 *  LinkedHashmap为有序，应该用该Map
		 */
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		/**
		 * 返回一个经过处理的shiroFilterFactoryBean 交给 spring 处理
		 */
		return shiroFilterFactoryBean;
	}
	
	/**
	 * 
	 * @param userRealm
	 * @return
	 */
	@Bean(name="defaultWebSecurityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("UserRealm")UserRealm userRealm) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		//关联Realm，将userRealm 对象设置到web默认的安全管理中
		defaultWebSecurityManager.setRealm(userRealm);
		return defaultWebSecurityManager;
		
	}
	/**
	 * 获取Realm
	 * @return
	 */
	@Bean(name="UserRealm")
	public UserRealm getRealm() {
		return new UserRealm();
	}
	
	/**
	 * 设置控制ThymeLeaf权限按钮
	 * @return ShiroDialect()
	 */
	@Bean(name="shiroDialect")
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
	
}
