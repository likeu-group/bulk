package cool.likeu.bulk.security.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cool.likeu.bulk.misc.BulkConstant.SECURITY_ACCESS_TOKEN_HTTP_HEADER;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <p>JwtToken过滤器</p>
 *
 * @author XiaoYu
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;

	private final JwtTokenManager jwtTokenManager;

	public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService,
			JwtTokenManager jwtTokenManager) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenManager = jwtTokenManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		final String authToken = request.getHeader(SECURITY_ACCESS_TOKEN_HTTP_HEADER);
		if (!StringUtils.isEmpty(authToken)) {
			final String username = jwtTokenManager.getUsernameFromToken(authToken);
			if (!StringUtils.isEmpty(username) && (SecurityContextHolder.getContext().getAuthentication() == null)) {
				final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				if (jwtTokenManager.validateToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication =
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}
