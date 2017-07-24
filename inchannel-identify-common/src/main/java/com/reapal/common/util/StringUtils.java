/**
 * $Revision 1.1$
 * $Date 2009-07-25 $
 * 
 * Copyright(C) 2009 Umessage Co,.Ltd. All rights reserved.
 */
package com.reapal.common.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Descriptor : Utility class to peform common String manipulation algorithms.
 */
public class StringUtils {

	// Constants used by escapeHTMLTags
	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
	private static final char[] AMP_ENCODE = "&amp;".toCharArray();
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	private StringUtils() {
		// Not instantiable.
	}

	/**
	 * Replaces all instances of oldString with newString in string.
	 * 
	 * @param string
	 *            the String to search to perform replacements on.
	 * @param oldString
	 *            the String that should be replaced by newString.
	 * @param newString
	 *            the String that will replace all instances of oldString.
	 * @return a String will all instances of oldString replaced by newString.
	 */
	public static String replace(String string, String oldString,
			String newString) {
		if (string == null) {
			return null;
		}
		int i = 0;
		// Make sure that oldString appears at least once before doing any
		// processing.
		if ((i = string.indexOf(oldString, i)) >= 0) {
			// Use char []'s, as they are more efficient to deal with.
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(string2.length);
			buf.append(string2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			// Replace all remaining instances of oldString with newString.
			while ((i = string.indexOf(oldString, i)) > 0) {
				buf.append(string2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(string2, j, string2.length - j);
			return buf.toString();
		}
		return string;
	}

	/**
	 * Replaces all instances of oldString with newString in line with the added
	 * feature that matches of newString in oldString ignore case.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line with the added
	 * feature that matches of newString in oldString ignore case. The count
	 * paramater is set to the number of replaces performed.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * @param count
	 *            a value that will be updated with the number of replaces
	 *            performed.
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static String replaceIgnoreCase(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			int counter = 1;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line. The count
	 * Integer is updated with number of replaces.
	 * 
	 * @param line
	 *            the String to search to perform replacements on.
	 * @param oldString
	 *            the String that should be replaced by newString.
	 * @param newString
	 *            the String that will replace all instances of oldString.
	 * @return a String will all instances of oldString replaced by newString.
	 */
	public static String replace(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 1;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuilder buf = new StringBuilder(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * This method takes a string and strips out all tags except <br>
	 * tags while still leaving the tag body intact.
	 * 
	 * @param in
	 *            the text to be converted.
	 * @return the input string with all tags removed.
	 */
	public static String stripTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuilder out = new StringBuilder((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
			} else if (ch == '<') {
				if (i + 3 < len && input[i + 1] == 'b' && input[i + 2] == 'r'
						&& input[i + 3] == '>') {
					i += 3;
					continue;
				}
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
			} else if (ch == '>') {
				last = i + 1;
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * This method takes a string which may contain HTML tags (ie, &lt;b&gt;,
	 * &lt;table&gt;, etc) and converts the '&lt'' and '&gt;' characters to
	 * their HTML escape sequences. It will also replace LF with &lt;br&gt;.
	 * 
	 * @param in
	 *            the text to be converted.
	 * @return the input string with the characters '&lt;' and '&gt;' replaced
	 *         with their HTML escape sequences.
	 */
	public static String escapeHTMLTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuilder out = new StringBuilder((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			} else if (ch == '\n') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append("<br>");
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * Escapes all necessary characters in the String so that it can be used in
	 * an XML doc.
	 * 
	 * @param string
	 *            the string to escape.
	 * @return the string with appropriate characters escaped.
	 */
	public static String escapeForXML(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuilder out = new StringBuilder((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * Unescapes the String by converting XML escape sequences back into normal
	 * characters.
	 * 
	 * @param string
	 *            the string to unescape.
	 * @return the string with appropriate characters unescaped.
	 */
	public static String unescapeFromXML(String string) {
		string = replace(string, "&lt;", "<");
		string = replace(string, "&gt;", ">");
		string = replace(string, "&quot;", "\"");
		return replace(string, "&amp;", "&");
	}

	/**
	 * Returns a collection of Strings as a comma-delimitted list of strings.
	 * 
	 * @return a String representing the Collection.
	 */
	public static String collectionToString(Collection<String> collection) {
		if (collection == null || collection.isEmpty()) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		String delim = "";
		for (String element : collection) {
			buf.append(delim);
			buf.append(element);
			delim = ",";
		}
		return buf.toString();
	}

	/**
	 * Returns a comma-delimitted list of Strings as a Collection.
	 * 
	 * @return a Collection representing the String.
	 */
	public static Collection<String> stringToCollection(String string) {
		if (string == null || string.trim().length() == 0) {
			return Collections.emptyList();
		}
		Collection<String> collection = new ArrayList<String>();
		StringTokenizer tokens = new StringTokenizer(string, ",");
		while (tokens.hasMoreTokens()) {
			collection.add(tokens.nextToken().trim());
		}
		return collection;
	}

	/**
	 * Abbreviates a string to a specified length and then adds an ellipsis if
	 * the input is greater than the maxWidth. Example input:
	 * 
	 * <pre>
	 *      user1@jivesoftware.com/home
	 * </pre>
	 * 
	 * and a maximum length of 20 characters, the abbreviate method will return:
	 * 
	 * <pre>
	 *      user1@jivesoftware.c...
	 * </pre>
	 * 
	 * @param str
	 *            the String to abbreviate.
	 * @param maxWidth
	 *            the maximum size of the string, minus the ellipsis.
	 * @return the abbreviated String, or <tt>null</tt> if the string was
	 *         <tt>null</tt>.
	 */
	public static String abbreviate(String str, int maxWidth) {
		if (null == str) {
			return null;
		}

		if (str.length() <= maxWidth) {
			return str;
		}

		return str.substring(0, maxWidth) + "...";
	}

	/**
	 * replace a Ctrl character in a string.
	 * 
	 * @param s
	 * @return
	 */
	public static final String htmlToCode(String s) {
		if (s == null) {
			return "";
		} else {
			s = s.replace("\n\n", "");
			s = s.replace("\r\n", "");
			return s;
		}
	}

	/**
	 * left - 返回字符串指定位数的左边字符串，一个中文算两位
	 */
	public static String left(String s, int n) {
		if (s == null) {
			return null;
		} else if (n < 1) {
			return "";
		} else {
			StringBuffer buf = new StringBuffer();
			int len = 0;
			int length = s.length();

			for (int i = 0; i < length; i++) {
				String ch = s.substring(i, i + 1);
				byte[] b;
				try {
					b = ch.getBytes("GBK"); // GBK编码中文会返回两个字节

				} catch (UnsupportedEncodingException e) {
					return null;
				}

				len += b.length;

				if (len > n) {
					break;
				} else {
					buf.append(ch);
				}
			}

			return buf.toString();
		}

	}

	/**
	 * right - 返回字符串指定位数的右边字符串，一个中文算两位
	 */
	public static String right(String s, int n) {
		if (s == null) {
			return null;
		} else if (n < 1) {
			return "";
		} else {
			String buf = new String();
			int len = 0;
			int length = s.length();

			for (int i = length; i > 0; i--) {
				String ch = s.substring(i - 1, i);
				byte[] b;
				try {
					b = ch.getBytes("GBK"); // GBK编码中文会返回两个字节

				} catch (UnsupportedEncodingException e) {
					return null;
				}

				len += b.length;

				if (len > n) {
					break;
				} else {
					buf = ch + buf;
				}
			}

			return buf;
		}
	}
	
	public static boolean isEmpty(String str) {
		str = str == null ? "" : str.trim();
		return str.equals("") ? true : false;
	}
	
	public static boolean isNotEmpty(String str) {
		str = str == null ? "" : str.trim();
		return str.equals("") ? false : true;
	}
	
	public static String emptyToStr(String str) {
		return str = str == null ? "" : str.trim();
	}

	/**
	 * 指定的字符串是否是日期格式类型
	 * @param str
	 * @param sdf
	 * @return
	 */
	public static boolean isDate(String str,SimpleDateFormat sdf) {
		boolean bFlag = false;
		if(sdf == null) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");	
		} 
		try {
			sdf.parse(str);
			bFlag = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return bFlag;
	}
	
	/**
	 * 指定的字符串是否是日期格式类型(yyyy-mm-dd)
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str) {
		return isDate(str,null);
	}
	
	/**
	 * 
	 * @param source 原字符串
	 * @param destStr 目标字符串
	 * @param split 分割符号
	 * @return
	 */
	public static boolean isContainStr(String source,String destStr,String split) {
		boolean bFlag = false;
		
		if(source != null && destStr != null) {
			if(split == null) {
				split = ",";
			}
			
			String[] tmpArray = source.split(split);
				if(tmpArray != null && tmpArray.length > 0) {
				for (int i = 0; i < tmpArray.length; i++) {
					if(tmpArray[i].equals(destStr)) {
						bFlag = true;
					}
				}
			}
		}
		
		return bFlag;
	}
	
	/**
	 * 字符串转换日期类型
	 * @param source
	 * @return
	 */
	public static Date strToDate(String source) {
		
		return strToDate(source,null);
	}
	
	/**
	 * 字符串转换日期类型
	 * @param source
	 * @param pattern
	 * @return
	 */
	public static Date strToDate(String source,String pattern) {
		if(isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.CHINA);
		try {
			return sdf.parse(source);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 字符串转换int
	 * @param str
	 * @return
	 */
	public static int strToInt(String str) {
		try {
			return new Integer(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	
	/**
	 * 字符串转换float
	 * @param str
	 * @return
	 */
	public static float strToFloat(String str) {
		try {
			return new Float(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	/**
	 * 格式化double数值
	 * @param num
	 * @return
	 */
	public static double formatNumber(double num,int point){
		String floatstr="#0.";
		for(int i=0;i<point;i++)
		{
			floatstr+="0";
		}
		DecimalFormat df = new DecimalFormat(floatstr);
		return Double.parseDouble(df.format(num));
	}
	/**
	 * 获取字符串数组
	 */
	public static String[] splitStrArray(String str){
		String lastStr = str.substring(str.length()-1, str.length());
		if(StringUtils.isNotEmpty(lastStr) && lastStr.equals(",")){
			str = str.substring(0, str.length()-1);
		}
		str = str.replaceAll(" ", "");
		return str.split(",");
	}
}