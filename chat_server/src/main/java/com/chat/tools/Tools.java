package com.chat.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 作�?? 刘志�?
 * @version 创建时间�?2018�?8�?17�? 下午2:52:18 类说�?
 */
public class Tools {
	
	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	private static final long ONE_WEEK = 604800000L;
 
	//private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟�?";
	private static final String ONE_HOUR_AGO = "小时�?";
	private static final String ONE_DAY_AGO = "天前";
	private static final String ONE_MONTH_AGO = "月前";
	private static final String ONE_YEAR_AGO = "年前";
	public static final String DATE_YEAR_MONTH_DAY = "yyyy-MM-dd";

	
	
	public static Date parseStr(String strDate) throws ParseException {
		if(strDate == null || "".equals(strDate)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_YEAR_MONTH_DAY);
		return sdf.parse(strDate);
	}
	
	public static String formateDate(Date date) {
		if(date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_YEAR_MONTH_DAY);
		return sdf.format(date).toString();
	}
	
	/**
	 * 转化为万 作�??:刘志�? 时间:2018�?8�?17�? 下午2:53:57
	 * 
	 * @param count
	 * @return
	 */
	public static String toThousand(Integer count) {
		BigDecimal bigDecimal = new BigDecimal(count);
		// 转换为万元（除以10000�?
		BigDecimal decimal = bigDecimal.divide(new BigDecimal("10000"));
		// 保留两位小数
		DecimalFormat formater = new DecimalFormat("0.0");
		// 四舍五入
		formater.setRoundingMode(RoundingMode.HALF_UP); // 5000008.89
		// 格式化完成之后得出结�?
		String formatNum = formater.format(decimal);
		return formatNum;
	}

	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签
		String s = htmlStr.trim();
		if (s.length() > 200) {
			return s.substring(0, 200);
		} else {
			return s; // 返回文本字符�?
		}
	}
	
	/**
	 * 文章内容空格和回车替换为前端代码
	 * 作�??:刘志�?
	 * 时间:Sep 27, 2018 2:51:14 PM
	 * @return
	 */
	public static String contextFormatToMini(String context) {
		context = context.replaceAll(" ","&ensp;");
		return context;
	}
	/**
	 * 文章内容空格和回车替换为前端代码
	 * 作�??:刘志�?
	 * 时间:Sep 27, 2018 2:51:14 PM
	 * @return
	 */
	public static String contextFormatToHTML(String context) {
		context = context.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		context = context.replaceAll(" ","&nbsp;");
		return context;
	}
	
	/**
	 * 修改阅读�?
	 * 作�??:刘志�?
	 * 时间:Sep 27, 2018 6:26:57 PM
	 * @param readsum
	 * @return
	 */
	public static Integer getReadSum(Integer readsum) {
		if(readsum==null) {
			return 0;
		}else if(readsum>0 && readsum<=1000) {
			return readsum*5;
		}else if(readsum>1000 && readsum<10000) {
			return readsum*2;
		}else {
			return readsum;
		}
	}
	
	public static String format(Date date) {
		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			return "刚刚";
			//return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
		}
		if (delta < 60L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 48L * ONE_HOUR) {
			return "昨天";
		}
		if (delta < 30L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		}
		if (delta < 12L * 4L * ONE_WEEK) {
			long months = toMonths(delta);
			return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
		} else {
			long years = toYears(delta);
			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
		}
	}
 
	private static long toSeconds(long date) {
		return date / 1000L;
	}
 
	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}
 
	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}
 
	private static long toDays(long date) {
		return toHours(date) / 24L;
	}
 
	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}
 
	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}
}
