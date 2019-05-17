package cn.jeeweb.web.ebp.notice.base.websockt;

public class Formatter {

	public static String formatSuccessLog(String log) {
		if (log == null || log.trim().length() == 0) {
			throw new IllegalArgumentException("log is null");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(log.trim());
		sb.append(" ");
		for (int i = log.length(); i < 80; i++) {
			sb.append(".");
		}
		sb.append(" ");
		sb.append("SUCCESS");
		return sb.toString();
	}
}
