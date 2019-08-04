# Java-studay
# Shiro的简单实现

### spring boot 整合shiro



1. ##### 配置XML文件

   ```xml
   导入这两个依赖包	
   <dependency>
   			<groupId>org.apache.shiro</groupId>
   			<artifactId>shiro-spring</artifactId>
   			<version>1.4.0</version>
   		</dependency>
   		
   		<dependency>
   			<groupId>com.github.theborakompanioni</groupId>
   			<artifactId>thymeleaf-extras-shiro</artifactId>
   			<version>2.0.0</version>
   		</dependency>
   ```

   

2. ##### 设置config 配置类或者配置文件 ，这里使用配置类的方法

   1. ```java
      /**
      *	 shiro 核心方法设置拦截器规则
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
      
      ```

   2. ```java
      /**
      * 关联Realm，将userRealm 对象设置到web默认的安全管理中
      */
      @Bean(name="defaultWebSecurityManager")
      public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("UserRealm")UserRealm userRealm) {
          
      		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
      		//关联Realm，将userRealm 对象设置到web默认的安全管理中
      		defaultWebSecurityManager.setRealm(userRealm);
      		return defaultWebSecurityManager;
      		
      	
      }
      ```

   3. ```java
      /**
      * 获取Realm
      */
      @Bean(name="UserRealm")
      public UserRealm getRealm() {
          return new UserRealm();
      }
      ```

   4. ```java
      /**
      	 * 设置控制ThymeLeaf权限按钮
      	 * @return ShiroDialect()
      	 */
      @Bean(name="shiroDialect")
      	public ShiroDialect getShiroDialect() {
      		return new ShiroDialect();
      	}
      ```

3. ##### 设计验证逻辑

   ```java
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
   
   
   ```

4. ##### 控制器层页面处理页面跳转

   ```java
   控制器层
   public class TestController {
   
   	@RequestMapping("themleaf")
   	public String themleaf(Model model) {
   		model.addAttribute("themleaf", "good !");
   		return "themleaf";
   	}
   	
   	@RequestMapping("/add")
   	public String add() {
   		return "/user/adduser";
   	}
   	@RequestMapping("/update")
   	public String update() {
   		return "/user/updateuser";
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
   			return "redirect:/themleaf";
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
   ```

5. ##### Html页面测试

   ```html
   <!--业务层页面  -->
   <!DOCTYPE html>
   <html xmlns="http://www.w3.org/1999/xhtml"
         xmlns:th="http://www.thymeleaf.org" //thymeleaf 标签库
         xmlns:shiro="http://www.pollix.at/thymeleaf/shiro" //shrio 标签库>
   <head>
   <meta charset="UTF-8">
   <title>Insert title here</title>
   </head>
   <body>
   <p th:text="${themleaf}"></p>
   <!--shiro:hasPermisson 权限验证  -->
   <div shiro:hasPermisson="user:add"><a href="/add">进入添加功能</a><br></div>
   
   <div shiro:hasPermisson="user:add"><a href="/update">进入更新功能</a><br></div>
   <a href="/tologin">返回登陆页面</a>
   </body>
   </html>
   
   
   <!--登陆页面  -->
   <!DOCTYPE html>
   <html xmlns="http://www.w3.org/1999/xhtml"
         xmlns:th="http://www.thymeleaf.org"><head>
   <meta charset="UTF-8">
   <title>Insert title here</title>
   </head>
   <body>
   <p>登陆</p>
   <p th:text="${msg }"></p>
   <form action="/login">
   账号<input type="text" name="name"><br>
   密码<input type="text" name="password">
   <button type="submit">登陆</button>
   </form>
   </body>
   </html>
   ```
