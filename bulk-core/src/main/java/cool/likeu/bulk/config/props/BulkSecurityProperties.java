package cool.likeu.bulk.config.props;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author XiaoYu
 * @since 2021/8/17 11:02
 * @version 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "bulk.security")
public class BulkSecurityProperties {

	private String signingKey;
	private Duration expirationTime;

	public Duration getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Duration expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getSigningKey() {
		return signingKey;
	}

	public void setSigningKey(String signingKey) {
		this.signingKey = signingKey;
	}

	public long getExpirationMilliseconds() {
		return getExpirationTime().getSeconds() * 1000;
	}

}
