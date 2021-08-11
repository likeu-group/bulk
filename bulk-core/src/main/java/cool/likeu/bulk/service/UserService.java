package cool.likeu.bulk.service;

import cool.likeu.bulk.repo.po.UserPO;

/**
 * <p>User Service</p>
 *
 * @see cool.likeu.bulk.service.RoleService
 * @since 1.0
 * @author XiaoYu
 */
public interface UserService extends RoleService, MenuService {

	/**
	 * <p>通过userId查找用户, 并携带Role信息</p>
	 *
	 * @see UserPO
	 * @see cool.likeu.bulk.repo.po.RolePO
	 * @see cool.likeu.bulk.repo.dao.RoleDao#selectRolesByUserId(Long)
	 * @param userId 用户ID
	 * @return UserPO
	 */
	UserPO lookupUserByUserId(Long userId);

	/**
	 * <p>通过username或email查找用户，便于后期接口同时支持username、email两种
	 * 登录方式获取用户信息</p>
	 *
	 * @see UserPO
	 * @see cool.likeu.bulk.repo.po.RolePO
	 * @see cool.likeu.bulk.repo.dao.RoleDao#selectRolesByUserId(Long)
	 * @param username 用户名或者是email, username代表用户登录的唯一凭证
	 * @return UserPO
	 */
	UserPO lookupUserByUsername(String username);

	/**
	 * <p>用户登录</p>
	 *
	 * @see cool.likeu.bulk.security.listener.DefaultAuthenticationSuccessEventListener
	 * @param username 用户名或email，username代表用户登录的唯一凭证
	 * @param password 密码
	 * @return jsonWebToken
	 */
	String login(final String username, final String password);

}
