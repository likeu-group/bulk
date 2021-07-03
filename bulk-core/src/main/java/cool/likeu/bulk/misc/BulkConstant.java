package cool.likeu.bulk.misc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class BulkConstant {

	public final static String EMPTY_STRING = "";

	public final static String SECURITY_ACCESS_TOKEN_HTTP_HEADER = "Access-Token";

	public final static String DEFAULT_CHARSET_ENCODING = "utf-8";

	public final static String MIME_TYPE_APPLICATION_JSON = "application/json;charset=" + DEFAULT_CHARSET_ENCODING;

	public final static Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	public final static String USER_CACHE_PREFIX = "bulk_user:";
}
