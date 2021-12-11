package cool.likeu.bulk.security.token;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cool.likeu.bulk.config.props.BulkSecurityProperties;
import cool.likeu.bulk.security.BulkUserDetailsImpl;
import cool.likeu.bulk.utils.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static cool.likeu.bulk.misc.BulkConstant.SECURITY_ACCESS_TOKEN_HTTP_HEADER;

@Component
public class JwtTokenManager {

	private final BulkSecurityProperties securityProps;

	private final static String CLAIM_KEY_USERNAME = Claims.SUBJECT;

	/**
	 * expire time millisecond
	 */
	@Deprecated
	public final static long EXPIRATION_TIME = 24 * 60 * 60 * 1000;

	/**
	 * secret key(sha512:source:bulk-xsdq-corporation:author@xiaoyu)
	 */
	@Deprecated
	private final static String SECURITY_SIGNING_KEY =
			"683d57561ce3aa0872c8ebdd700aab2c4a75fd080db36d58288fea1eda95276288340ddcf0baff497fefe6f93fbb26eb7895ef929dfa1dbb11a1273ee6f82fb5";

	public JwtTokenManager(BulkSecurityProperties securityProps) {
		this.securityProps = securityProps;
	}

	/**
	 * <p>创建json web token</p>
	 *
	 * @param userDetails {@link UserDetails}
	 * @return jsonWebToken
	 */
	public String createToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>(16);
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(Instant.now().toEpochMilli() + securityProps.getExpirationMilliseconds()))
				.signWith(SignatureAlgorithm.HS512, securityProps.getSigningKey())
				.compact();
	}

	/**
	 * <p>续签令牌</p>
	 *
	 * @param claims 已有的claims
	 * @return new JsonWebToken
	 */
	public String renewToken(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + securityProps.getExpirationMilliseconds()))
				.signWith(SignatureAlgorithm.HS512, securityProps.getSigningKey())
				.compact();
	}

	/**
	 * <p>验证token是否过期</p>
	 *
	 * @param token jsonWebToken
	 * @param userDetails UserDetails
	 * @return success | failure
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		BulkUserDetailsImpl securityUser = (BulkUserDetailsImpl) userDetails;
		String username = getUsernameFromToken(token);
		return (username.equals(securityUser.getUsername()) && !isTokenExpired(token));
	}

	/**
	 * <p>判断token是否过期</p>
	 *
	 * @param token jsonWebToken
	 * @return true | false
	 */
	public boolean isTokenExpired(String token) {
		return getExpirationDateFromToken(token).before(new Date());
	}

	/**
	 * <p>刷新令牌</p>
	 *
	 * @param token JsonWebToken
	 * @return new JsonWebToken
	 */
	public String refreshToken(String token) {
		Claims claims = getClaimsFromToken(token);
		claims.put("created", new Date());
		return renewToken(claims);
	}

	public String getUsernameFromRequest(HttpServletRequest request) {
		final String authToken = request.getHeader(SECURITY_ACCESS_TOKEN_HTTP_HEADER);
		if (StringUtils.isEmpty(authToken)) {
			return null;
		}
		return getUsernameFromToken(authToken);
	}

	/**
	 * <p>从token中获取用户名</p>
	 *
	 * @param token jsonWebToken
	 * @return username
	 */
	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token).getExpiration();
	}

	private Claims getClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(securityProps.getSigningKey())
				.parseClaimsJws(token)
				.getBody();
	}

}
