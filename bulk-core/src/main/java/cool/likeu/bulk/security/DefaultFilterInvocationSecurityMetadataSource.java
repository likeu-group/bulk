package cool.likeu.bulk.security;

import java.util.Collection;

import cool.likeu.bulk.service.MenuService;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

/**
 *
 * @see FilterInvocation
 *
 * @author XiaoYu
 * @since 2021/08/13
 * @version 1.0.0
 */
@Component
public class DefaultFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private MenuService menuService;

	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Collection<ConfigAttribute> getAttributes(Object invocation) throws IllegalArgumentException {
		// TODO 应该在此处加载权限规则了。使用anyMatches应该不起作用了
		String requestUrl = ((FilterInvocation) invocation).getRequestUrl();
		if (antPathMatcher.match("/api/auth/login", requestUrl)) {
			return SecurityConfig.createList("ROLE_ANONYMOUS");
		}
		/*
		1. FilterInvocationSecurityMetadataSource提供ConfigAttributes，ConfigAttribute为Authorization的属性配置
		2. AccessDecisionManager则是限制Authentication中携带的GrantedAuthority是否与ConfigAttribute匹配
		 */
		return SecurityConfig.createList("Role_login", "sys");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}
}
