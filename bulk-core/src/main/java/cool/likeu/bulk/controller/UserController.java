package cool.likeu.bulk.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import cool.likeu.bulk.dto.BulkResponse;
import cool.likeu.bulk.dto.UserInformation;

import static cool.likeu.bulk.misc.BulkConstant.SECURITY_ACCESS_TOKEN_HTTP_HEADER;

import cool.likeu.bulk.repo.po.UserPO;
import cool.likeu.bulk.repo.repository.UserRepository;
import cool.likeu.bulk.security.token.JwtTokenManager;
import cool.likeu.bulk.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/info")
	public BulkResponse<UserInformation> lookupUserInfo(HttpServletRequest request) {
		final String authToken = request.getHeader(SECURITY_ACCESS_TOKEN_HTTP_HEADER);
		final String username = jwtTokenManager.getUsernameFromToken(authToken);
		final UserPO cacheUserPO = userRepository.lookupUserByCache(username);
		final UserInformation userInformation = UserInformation.convert(cacheUserPO);

		return BulkResponse.success(userInformation);
	}

	/**
	 * TODO
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/nav")
	public BulkResponse<Object> lookupNavigationList(HttpServletRequest request) throws IOException {
		final String authToken = request.getHeader(SECURITY_ACCESS_TOKEN_HTTP_HEADER);
		final String username = jwtTokenManager.getUsernameFromToken(authToken);
		final UserPO cacheUserPO = userRepository.lookupUserByCache(username);

		return null;
	}

}
