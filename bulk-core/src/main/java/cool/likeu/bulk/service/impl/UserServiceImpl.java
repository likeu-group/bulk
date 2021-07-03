package cool.likeu.bulk.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cool.likeu.bulk.repo.dao.MenuDao;
import cool.likeu.bulk.repo.dao.RoleDao;
import cool.likeu.bulk.repo.dao.UserDao;
import cool.likeu.bulk.repo.po.MenuPO;
import cool.likeu.bulk.repo.po.RolePO;
import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.repo.repository.UserRepository;
import cool.likeu.bulk.security.BulkUserDetailsImpl;
import cool.likeu.bulk.security.token.JwtTokenManager;
import cool.likeu.bulk.service.MenuService;
import cool.likeu.bulk.service.RoleService;
import cool.likeu.bulk.service.UserService;

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
	 * TODO 移除lazyLoad, Role加载的问题
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
	 * TODO 移除lazyLoad, Role加载的问题
	 *
	 * @param username 用户名
	 * @return
	 */
	@Override
	@Cacheable(cacheNames = "bulk_user", key = "#username")
	public UserPO lookupUserByUsername(String username) {
		return userDao.selectByUsername(username);
	}

	@Override
	public UserPO lookupUserByEmail(String email) {
		return userDao.selectByEmail(email);
	}

	@Override
	public UserPO lookupUserByParseRequestToken(HttpServletRequest request) {
		String username = tokenManager.getUsernameFromRequest(request);
		return lookupUserByUsername(username);
	}

	@Override
	public String login(final String username, final String password) {
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
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
	public void lookupMenuByRoleId(Long roleId) {
		List<MenuPO> menus = menuDao.selectByRoleId(roleId);
	}
}
