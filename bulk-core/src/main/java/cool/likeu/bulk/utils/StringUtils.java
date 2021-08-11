package cool.likeu.bulk.utils;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

public class StringUtils extends org.springframework.util.StringUtils {

	private final static String EMAIL_REGEX = "[a-zA-Z0-9_]+@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9]+)+";

	private final static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	public static final String ENCODE = "UTF-8";

	public static final String EMPTY = "";

	public static String newStringForUtf8(byte[] bytes) {
		return new String(bytes, Charset.forName(ENCODE));
	}

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}

	public static boolean isEmail(String str) {
		return EMAIL_PATTERN.matcher(str).matches();
	}
}
