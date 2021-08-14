package cool.likeu.bulk.config;

import cool.likeu.bulk.security.DefaultFilterInvocationSecurityMetadataSource;
import cool.likeu.bulk.security.token.JwtAuthenticationTokenFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <p>Spring-Security权限管理核心配置</p>
 *
 * @author XiaoYu
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	/**
	 * 退出登录成功处理器
	 */
	private final LogoutSuccessHandler logoutSuccessHandler;

	/**
	 * 请求访问被拒处理器
	 */
	private final AccessDeniedHandler accessDeniedHandler;

	/**
	 * AuthorizationManager (授权管理器)
	 */
	private final AccessDecisionManager accessDecisionManager;

	private final AuthenticationEntryPoint authenticationEntryPoint;

	private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

	/**
	 *
	 */
	private final FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

	public SpringSecurityConfig(
			UserDetailsService userDetailsService,
			LogoutSuccessHandler logoutSuccessHandler,
			AccessDeniedHandler accessDeniedHandler,
			AccessDecisionManager accessDecisionManager,
			AuthenticationEntryPoint authenticationEntryPoint,
			JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
			FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
		this.userDetailsService = userDetailsService;
		this.logoutSuccessHandler = logoutSuccessHandler;
		this.accessDeniedHandler = accessDeniedHandler;
		this.accessDecisionManager = accessDecisionManager;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
		this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
	}

	/**
	 * <p>配置UserDetails的数据源、密码加密格式</p>
	 *
	 * @see org.springframework.security.authentication.AuthenticationManager
	 * @param auth {@link AuthenticationManagerBuilder}
	 * @throws Exception e
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(defaultPasswordEncoder());
	}

	/**
	 * <p>Http资源的配置</p>
	 * <ul>
	 *     <li>anyRequest - 匹配所有请求路径</li>
	 *     <li>access - SpringEl表达式结果为true时可以访问</li>
	 *     <li>anonymous - 匿名可以访问</li>
	 *     <li>denyAll - 用户不能访问</li>
	 *     <li>fullyAuthenticated - 用户完全认证可以访问（非remember-me下自动登录）</li>
	 *     <li>hasAnyAuthority - 如果有参数，参数表示权限，则其中任何一个权限可以访问</li>
	 *     <li>hasAnyRole - 如果有参数，参数表示角色，则其中任何一个角色可以访问</li>
	 *     <li>hasAuthority - 如果有参数，参数表示权限，则其权限可以访问</li>
	 *     <li>hasIpAddress - 如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问</li>
	 *     <li>hasRole - 如果有参数，参数表示角色，则其角色可以访问</li>
	 *     <li>permitAll - 用户可以任意访问</li>
	 *     <li>rememberMe - 允许通过remember-me登录的用户访问</li>
	 *     <li>authenticated - 用户登录后可访问</li>
	 * </ul>
	 * TODO HttpSecurity的配置
	 *
	 * @param http {@link HttpSecurity}
	 * @throws Exception e
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/api/auth/login", "/api/auth/captcha").permitAll()
				.antMatchers(
						HttpMethod.GET,
						"/*.html",
						"/**/*.html",
						"/**/*.css",
						"/**/*.js",
						"/**/*.ico"
				).permitAll()
				.anyRequest().authenticated()
				.and()
				.rememberMe()
				.and()
				.headers().frameOptions().disable()
				.and()
				.logout()
				.logoutUrl("/api/auth/logout")
				.logoutSuccessHandler(logoutSuccessHandler)
				.and()
				.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(defaultCorsFilter(), JwtAuthenticationTokenFilter.class)
				.addFilterBefore(defaultCorsFilter(), LogoutFilter.class)
				.addFilterAfter(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
	}

	@Bean(value = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder defaultPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * <p>跨域配置</p>
	 *
	 * @return {@link CorsFilter}
	 */
	@Bean
	public CorsFilter defaultCorsFilter() {
		final UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		configurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(configurationSource);
	}

	FilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {
		FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
		interceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
		interceptor.setAccessDecisionManager(accessDecisionManager);
		interceptor.setAuthenticationManager(authenticationManager());
		return interceptor;
	}

// http://www.semlinker.com/spring-security-remember-me/
//
//	/**
//	 * 还是应该考虑使用rememberMe配合前端使用
//	 * rememberMe持久化配置
//	 * @return
//	 */
//	@Bean
//	public PersistentTokenRepository persistentTokenRepository() {
//		return null;
//	}
}
