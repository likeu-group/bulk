package cool.likeu.bulk.security;

import java.util.ArrayList;
import java.util.Collection;

import cool.likeu.bulk.dto.BulkResponse.HttpStatus;
import cool.likeu.bulk.exception.runtime.BulkRuntimeException;
import cool.likeu.bulk.repo.po.MenuPO;
import cool.likeu.bulk.repo.po.PermissionPO;
import cool.likeu.bulk.service.MenuService;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * <ol>
 *     <li>FilterInvocationSecurityMetadataSource提供ConfigAttributes，ConfigAttribute为Authorization的属性配置</li>
 *     <li>AccessDecisionManager则是限制Authentication中携带的GrantedAuthority是否与ConfigAttribute匹配</li>
 * </ol>
 *
 * @see FilterInvocation
 *
 * @author XiaoYu
 * @since 2021/08/13
 * @version 1.0.0
 */
@Component
public class DefaultFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private final MenuService menuService;

	public DefaultFilterInvocationSecurityMetadataSource(MenuService menuService) {
		this.menuService = menuService;
	}

	/**
	 * 该方法返回本次访问需要的权限，可以有多个权限。在上面的实现中如果没有匹配的url直接返回null，
	 * 也就是没有配置权限默认都为白名单，想要换成默认是黑名单只要修改这里即可。
	 *
	 * @param invocation Maybe is FilterInvocation
	 * @return permissions required for current access.
	 * @throws IllegalArgumentException exception
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object invocation) throws IllegalArgumentException {
		// TODO 应该在此处加载权限规则了。使用antMatches应该不起作用了
		// TODO （ANONYMOUS 匿名者访问或者是为null.）是否需要ANONYMOUS？必须要，因为/api/auth/login也要在menu中配置
		FilterInvocation filterInvocation = (FilterInvocation) invocation;

		Collection<MenuPO> menus = menuService.listMenus();
		if (ObjectUtils.isEmpty(menus)) {
			throw new BulkRuntimeException(HttpStatus.INTERNAL_ERROR,
					"No configuration menu and permission information, please check it.");
		}
		for (MenuPO menu : menus) {
			final String allowHttpMethod = menu.getMenuMethod();
			final String menuUrl = menu.getMenuUrl();
			final RequestMatcher requestMatcher = new AntPathRequestMatcher(menuUrl, allowHttpMethod);

			if (requestMatcher.matches(filterInvocation.getRequest())) {
				final Collection<PermissionPO> permissions = menu.getPermissions();
				final Collection<ConfigAttribute> requirePermissions = new ArrayList<>(permissions.size());
				for (PermissionPO permission : permissions) {
					requirePermissions.add(new SecurityConfig(permission.getPermsEntity()));
				}
				return requirePermissions;
			}
		}
		return null;
	}

	/**
	 * 该方法如果返回了所有定义的权限资源，SpringSecurity会在启动时校验每个ConfigAttribute
	 * 是否配置正确，不需要校验直接返回null.
	 *
	 * @return if you need to check the defined permission resources when
	 * SpringSecurity starts running, you must return all ConfigAttributes,
	 * otherwise it return null.
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 该方法判断类对象是否支持校验，web项目一般使用{@link FilterInvocation}来判断，
	 * 或者直接返回true，而非web项目一般使用的是{@link org.aopalliance.intercept.MethodInvocation}
	 *
	 * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#getSecureObjectClass()
	 * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#afterPropertiesSet()
	 * @param clazz Maybe is a FilterInvocation
	 * @return is FilterInvocation
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}
