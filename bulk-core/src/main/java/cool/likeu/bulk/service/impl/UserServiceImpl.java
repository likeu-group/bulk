package cool.likeu.bulk.service.impl;

import java.util.Collection;
import java.util.List;

import cool.likeu.bulk.dto.MenuExposer;
import cool.likeu.bulk.repo.dao.MenuDao;
import cool.likeu.bulk.repo.dao.RoleDao;
import cool.likeu.bulk.repo.dao.UserDao;
import cool.likeu.bulk.repo.po.MenuPO;
import cool.likeu.bulk.repo.po.RolePO;
import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.security.BulkUserDetailsImpl;
import cool.likeu.bulk.security.token.JwtTokenManager;
import cool.likeu.bulk.service.MenuService;
import cool.likeu.bulk.service.RoleService;
import cool.likeu.bulk.service.UserService;
import cool.likeu.bulk.utils.StringUtils;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService, RoleService, MenuService {

	private final UserDao userDao;
	private final RoleDao roleDao;
	private final MenuDao menuDao;

	private final JwtTokenManager tokenManager;
	private final AuthenticationManager authenticationManager;

	public UserServiceImpl(UserDao userDao, RoleDao roleDao, MenuDao menuDao,
			JwtTokenManager tokenManager, @Lazy AuthenticationManager authenticationManager) {
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.menuDao = menuDao;
		this.tokenManager = tokenManager;
		this.authenticationManager = authenticationManager;
	}

	/**
	 *
	 * @param userId 用户ID
	 * @return
	 */
	@Override
	@Cacheable(cacheNames = "bulk_user", key = "#userId")
	public UserPO lookupUserByUserId(Long userId) {
		return userDao.selectByUserId(userId);
	}

	/**
	 * TODO @Cacheable无法缓存时间
	 *
	 * @param username 用户名或邮箱
	 * @return UserPO
	 */
	@Override
	@Cacheable(cacheNames = "bulk_user", key = "#username")
	public UserPO lookupUserByUsername(String username) {
		if (StringUtils.isEmail(username)) {
			return userDao.selectByEmail(username);
		}
		return userDao.selectByUsername(username);
	}

	@Override
	public String login(final String username, final String password) {
		// 1. security校验用户用户是否存在，密码是否相同
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
		// 2. 校验用户状态
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		BulkUserDetailsImpl userDetails = (BulkUserDetailsImpl) authentication.getPrincipal();

		return tokenManager.createToken(userDetails);
	}

	@Override
	public List<RolePO> lookupRolesByUserId(Long userId) {
		return roleDao.selectRolesByUserId(userId);
	}

	@Override
	public boolean assignRoleToUser(Long userId, Long roleId) {
		return false;
	}

	@Override
	public Collection<MenuExposer> exposeMenuByCorrespondRole(Long roleId) {
		List<MenuPO> menus = menuDao.selectByRoleId(roleId);
		return null;
	}

	@Override
	public Collection<MenuPO> listMenus() {
		menuDao.listMenus();
		return null;
	}
}
