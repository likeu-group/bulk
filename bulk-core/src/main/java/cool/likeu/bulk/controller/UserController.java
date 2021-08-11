package cool.likeu.bulk.controller;

import java.io.IOException;
import java.util.List;

import cool.likeu.bulk.dto.BulkResponse;
import cool.likeu.bulk.dto.UserInformation;
import cool.likeu.bulk.repo.po.RolePO;
import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.repo.repository.UserRepository;
import cool.likeu.bulk.security.token.JwtTokenManager;
import cool.likeu.bulk.service.UserService;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cool.likeu.bulk.misc.BulkConstant.SECURITY_ACCESS_TOKEN_HTTP_HEADER;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	private final UserRepository userRepository;

	private final JwtTokenManager jwtTokenManager;

	public UserController(UserService userService, UserRepository userRepository,
			JwtTokenManager jwtTokenManager) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.jwtTokenManager = jwtTokenManager;
	}

	/**
	 * TODO 1. 完成逻辑，完成该逻辑后才能往下做
	 *
	 * @param accessToken Access-Token
	 * @return user information
	 */
	@GetMapping("/info")
	public BulkResponse<UserInformation> obtainUserInfo(
			@RequestHeader(SECURITY_ACCESS_TOKEN_HTTP_HEADER) String accessToken) {
		// 该接口获取role、permission等，第一次登录并没有缓存，查询完成后，做好缓存信息
		// bulk_rbac:username x  -> user information { user base, role, permission }
		String username = jwtTokenManager.getUsernameFromToken(accessToken);
		UserPO userPO = userService.lookupUserByUsername(username);
		if (ObjectUtils.isEmpty(userPO)) {
			throw new IllegalArgumentException("非法的access-token.");
		}
		else {

		}

		UserInformation userInformation = UserInformation.convert(userPO);

		return BulkResponse.success(userInformation);
	}

	/**
	 * TODO 获取用户菜单
	 *
	 * @param accessToken Access-Token
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/nav")
	public BulkResponse<Object> obtainMenusInfo(
			@RequestHeader(SECURITY_ACCESS_TOKEN_HTTP_HEADER) String accessToken) {
		// 该接口获取menu，第一次登录并没有缓存，查询完成后，做好缓存信息 bulk_menu:username
		final String username = jwtTokenManager.getUsernameFromToken(accessToken);
		final UserPO cacheUserPO = userRepository.lookupUserByCache(username);

		return null;
	}

}
