package cool.likeu.bulk.service;

import java.util.List;

import cool.likeu.bulk.repo.po.RolePO;

/**
 * <p>Role Service</p>
 *
 * @since 1.0
 * @author XiaoYu
 */
public interface RoleService {

	/**
	 * <p>通过userId查找用户所有的角色ID</p>
	 *
	 * @see cool.likeu.bulk.repo.po.UserPO
	 * @see RolePO
	 * @param userId 用户ID
	 * @return RolePO
	 */
	List<RolePO> lookupRolesByUserId(Long userId);

	/**
	 * <p>为指定用户分配角色</p>
	 *
	 * @param userId 用户ID
	 * @param roleId 角色ID
	 * @return result
	 */
	boolean assignRoleToUser(Long userId, Long roleId);

}
