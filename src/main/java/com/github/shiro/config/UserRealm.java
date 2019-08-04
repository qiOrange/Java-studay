package com.github.shiro.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.github.shiro.User;
/**
 * 验证逻辑，shiro 业务层 认证用户是否具有相应的权限
 * 规则 com.github.shiro.config.shiroConfig 在该类中实现
 * 继承AuthorizingRealm 类，重写AuthorizationInfo AuthenticationInfo两个方法
 * doGetAuthorizationInfo 执行授权逻辑
 * doGetAuthenticationInfo 执行认证逻辑
 */
public class UserRealm extends AuthorizingRealm{
	
	/**
	 * 执行授权逻辑
	 * 实现权限分级
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.err.println("执行授权逻辑");
		//获取单一的经过验证用户的信息
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取subject 对象
		Subject subject=SecurityUtils.getSubject();
		//获取PrincipalCollection principals 参数中的验证用户信息
		User user = (User) subject.getPrincipal();
		//验证规则中是否有该字段 没有则会验证不通过
		info.addStringPermission(user.getPerm());
		return info;
	}
	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.err.println("执行认证逻辑");
		String admin="admin";
		String root="root";
		String perm=null;
		String password="123";
		//有控制器层获取token 数据
		UsernamePasswordToken passwordToken=(UsernamePasswordToken)token;
		String username = passwordToken.getUsername();
		System.out.println(username);
		
		if (username.equals(admin)) {
			perm="user:add";
		}
		if (username.equals(root)) {
			perm="user:update";
		}
		/**
		 * 这里是认证用户判断
		 */
		if (!username.equals(admin)&&!username.equals(root)) {
			//返回null 则验证不通过
			return null;
		}
		User user=new User(username, password, perm);
		/**
		 * SimpleAuthenticationInfo的第一个参数我么可以放入我们想要放入才参数，
		 * 如这里的对象User，我们还能存入id或Username等等，这个参数体现在本类处理权限的方法中：
		 */
		return new SimpleAuthenticationInfo(user, password,"");
	}
	/**
	 * 盐解析类
	 */
//	@Override
//	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
//	HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
//    matcher.setHashAlgorithmName("md5");//设置解密算法为：md5
//    matcher.setHashIterations(1);//设置解密次数为1次	
//		super.setCredentialsMatcher(credentialsMatcher);
//	}

}
