package cool.likeu.bulk.controller;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.node.ObjectNode;
import cool.likeu.bulk.dto.BulkResponse;
import cool.likeu.bulk.service.UserService;
import cool.likeu.bulk.utils.JacksonUtils;
import lombok.Data;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cool.likeu.bulk.security.token.JwtTokenManager.EXPIRATION_TIME;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;

	public AuthController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * <p>用户登录</p>
	 *
	 * @see cool.likeu.bulk.security.listener.DefaultAuthenticationSuccessEventListener
	 * @param userVO {username, password} RequestBody
	 * @return jsonWebToken
	 */
	@PostMapping("/login")
	public BulkResponse<ObjectNode> login(@RequestBody @Validated UserVO userVO) {
		final String token = userService.login(userVO.getUsername(), userVO.getPassword());
		final ObjectNode tokenJsonNode = JacksonUtils.createEmptyJsonNode()
				.put("token", token)
				// TODO 未使用application.yaml配置中的expire-time
				.put("expire", EXPIRATION_TIME);
		return BulkResponse.success(tokenJsonNode);
	}

	@Data
	static class UserVO {

		@NotNull(message = "用户名不能为空")
		private String username;

		@NotNull(message = "密码不能为空")
		private String password;

	}
}
