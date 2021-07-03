package cool.likeu.bulk.utils;

import java.nio.charset.Charset;

public class StringUtils extends org.springframework.util.StringUtils {

	public static final String ENCODE = "UTF-8";

	public static final String EMPTY = "";

	public static String newStringForUtf8(byte[] bytes) {
		return new String(bytes, Charset.forName(ENCODE));
	}

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}

}
