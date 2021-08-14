package cool.likeu.bulk.security;

import java.util.Collection;

import cool.likeu.bulk.repo.po.PermissionPO;
import cool.likeu.bulk.repo.po.RolePO;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author XiaoYu
 * @since 2021/8/13 14:38
 * @version 1.0.0
 */
public class BulkGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 6323616506699006824L;

	private final RolePO role;
	private final Collection<? extends PermissionPO> permissions;

	public BulkGrantedAuthority(RolePO role) {
		this.role = role;
		this.permissions = role.getPermsCollections();
	}

	@Override
	public String getAuthority() {
		return this.role.getRoleEntity();
	}

	public Collection<? extends PermissionPO> getPermissions() {
		return permissions;
	}

	public RolePO getRole() {
		return role;
	}
}
