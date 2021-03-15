package cool.likeu.bulk.generator.utils;

public class Underline2Camel {

	public static String underline2Camel(String line, boolean... firstIsUpperCase) {
		String str = "";

		if (line == null || line.isEmpty()) {
			return str;
		}
		else {
			StringBuilder sb = new StringBuilder();
			String[] strArr;
			// 不包含下划线，且第二个参数是空的
			if (!line.contains("_") && firstIsUpperCase.length == 0) {
				sb.append(line.substring(0, 1).toLowerCase()).append(line.substring(1));
				str = sb.toString();
			}
			else if (!line.contains("_") && firstIsUpperCase.length != 0) {
				if (!firstIsUpperCase[0]) {
					sb.append(line.substring(0, 1).toLowerCase()).append(line.substring(1));
					str = sb.toString();
				}
				else {
					sb.append(line.substring(0, 1).toUpperCase()).append(line.substring(1));
					str = sb.toString();
				}
			}
			else if (line.contains("_") && firstIsUpperCase.length == 0) {
				strArr = line.split("_");
				for (String s : strArr) {
					sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
				}
				str = sb.toString();
				str = str.substring(0, 1).toLowerCase() + str.substring(1);
			}
			else if (line.contains("_") && firstIsUpperCase.length != 0) {
				strArr = line.split("_");
				for (String s : strArr) {
					sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
				}
				if (!firstIsUpperCase[0]) {
					str = sb.toString();
					str = str.substring(0, 1).toLowerCase() + str.substring(1);
				}
				else {
					str = sb.toString();
				}
			}
		}
		return str;
	}
}

