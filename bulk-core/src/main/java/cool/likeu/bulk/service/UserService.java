package cool.likeu.bulk.service;

import javax.servlet.http.HttpServletRequest;

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
	 * <p>通过username查找用户，并使用{@code Mybatis}的LazyLoad加载
	 * 该用户对应的角色集合</p>
	 *
	 * @see UserPO
	 * @see cool.likeu.bulk.repo.po.RolePO
	 * @see cool.likeu.bulk.repo.dao.RoleDao#selectRolesByUserId(Long)
	 * @param username 用户名
	 * @return UserPO
	 */
	UserPO lookupUserByUsername(String username);

	/**
	 * <p>通过email查找用户，并使用{@code Mybatis}的LazyLoad加载
	 * 该用户对应的角色集合</p>
	 *
	 * @see UserPO
	 * @see cool.likeu.bulk.repo.po.RolePO
	 * @see cool.likeu.bulk.repo.dao.RoleDao#selectRolesByUserId(Long)
	 * @param email 用户邮箱
	 * @return UserPO
	 */
	UserPO lookupUserByEmail(String email);

	/**
	 * <p>使用从{@link HttpServletRequest}中获取的{@code Access-Token}查询用户信息，
	 * 该接口优先查询缓存，若缓存中不存在，则从数据库中获取</p>
	 * TODO 暂时不实现
	 * @param request Access-Token
	 * @return UserPO
	 */
	UserPO lookupUserByParseRequestToken(HttpServletRequest request);

	/**
	 * <p>用户登录</p>
	 *
	 * @see cool.likeu.bulk.security.listener.DefaultAuthenticationSuccessEventListener
	 * @param username 用户名
	 * @param password 密码
	 * @return jsonWebToken
	 */
	String login(final String username, final String password);

}
