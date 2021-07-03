package cool.likeu.bulk.security;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class DefaultFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Collection<ConfigAttribute> getAttributes(Object invocation) throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) invocation).getRequestUrl();
		/*
		1. FilterInvocationSecurityMetadataSource提供ConfigAttributes，ConfigAttribute为Authorization的属性配置
		2. AccessDecisionManager则是限制Authentication中携带的GrantedAuthority是否与ConfigAttribute匹配
		 */
		return SecurityConfig.createList("Role_login");
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
