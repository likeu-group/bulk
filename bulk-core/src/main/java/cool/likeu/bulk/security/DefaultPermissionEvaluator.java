package cool.likeu.bulk.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

// https://blog.csdn.net/cloume/article/details/83790111
public class DefaultPermissionEvaluator implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject,
			Object permission) {
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId,
			String targetType, Object permission) {
		return false;
	}
}
