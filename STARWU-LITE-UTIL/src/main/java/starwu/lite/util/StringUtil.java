package starwu.lite.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * @ClassName: StringUtil
 * @Description: String工具类
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class StringUtil {

	private static final int MD5LENGTH = 32;

	/**
	 * 汉语中数字大写
	 */
	private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾",
			"佰", "仟", "兆", "拾", "佰", "仟" };

	/**
	 * 特殊字符：整
	 */
	private static final String CN_FULL = "整";

	/**
	 * 特殊字符：负
	 */
	private static final String CN_NEGATIVE = "负";

	/**
	 * 金额的精度，默认值为2
	 */
	private static final int MONEY_PRECISION = 2;

	/**
	 * 特殊字符：零元整
	 */
	private static final String CN_ZEOR_FULL = "零元" + CN_FULL;

	/**
	 * 
	 * @Title: replaceFirstToUp
	 * @Description:将首字母大写
	 * @param str
	 * @return
	 */
	public static String replaceFirstToUp(String str) {
		return str = str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 
	 * @Title: isContainChina
	 * @Description:判断字符串中是否包含中文
	 * @param str
	 * @return
	 */
	public static boolean isContainChina(String str) {
		if (str == null || str.length() <= 0) {
			return false;
		}
		if (str.getBytes().length == str.length()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Title: flushChar
	 * @Description:字符串填充
	 * @param c
	 *            填充字符
	 * @param length
	 *            填充后长度
	 * @param str
	 * @param flushType
	 *            填充类型 0:向左填充  其它:向右填充
	 * @return
	 */
	public static String flushChar(char c, long length, String str, int flushType) {
		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < length) {
			sb = new StringBuffer();
			if (flushType == 0) {
				sb.append(c).append(str);// 左补
			} else {
				sb.append(str).append(c); // 右补
			}
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}

	/**
	 * 
	 * @Title: flushZeroLeft4MD5
	 * @Description:对MD5字符串进行填充
	 * @param str
	 * @return
	 */
	public static String flushZeroLeft4MD5(String str) {
		if (str != null && !"".equals(str)) {
			if (str.length() < MD5LENGTH) {
				return flushChar('0', MD5LENGTH, str, 0);
			}
		}
		return str;
	}

	/**
	 * 
	 * @Title: formatAmount2CNMontrayUnit
	 * @Description:将金额转换为中文大写
	 * @param amount
	 * @return
	 */
	public static String formatAmount2CNMontrayUnit(BigDecimal amount) {
		StringBuffer sb = new StringBuffer();

		int signum = amount.signum();
		if (signum == 0) {
			return CN_ZEOR_FULL;
		}

		// 这里会进行金额的四舍五入
		long number = amount.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();

		/**
		 * 得到小数点后两位值
		 */
		long scale = number % 100;
		int numUnit = 0;
		int numIndex = 0;
		boolean getZero = false;
		// 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
		if (!(scale > 0)) {
			numIndex = 2;
			number = number / 100;
			getZero = true;
		}

		if ((scale > 0) && (!(scale % 10 > 0))) {
			numIndex = 1;
			number = number / 10;
			getZero = true;
		}

		int zeroSize = 0;
		while (true) {
			if (number <= 0) {
				break;
			}
			// 每次获取到最后一个数
			numUnit = (int) (number % 10);
			if (numUnit > 0) {
				if ((numIndex == 9) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
				}
				if ((numIndex == 13) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
				}
				sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				getZero = false;
				zeroSize = 0;
			} else {
				++zeroSize;
				if (!(getZero)) {
					sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				}
				if (numIndex == 2) {
					if (number > 0) {
						sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
					}
				} else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				}
				getZero = true;
			}
			// 让number每次都去掉最后一个数
			number = number / 10;
			++numIndex;
		}
		// 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
		if (signum == -1) {
			sb.insert(0, CN_NEGATIVE);
		}
		// 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
		if (!(scale > 0)) {
			sb.append(CN_FULL);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @Title: formatAmount
	 * @Description:格式花金额，添加千分符
	 * @param amount
	 * @return
	 */
	public static String formatAmount(BigDecimal amount) {
		DecimalFormat df = new DecimalFormat(",###,##0.00");
		return df.format(amount);
	}

	/**
	 * 
	 * @Title: formatCount
	 * @Description:格式化笔数
	 * @param count
	 * @return
	 */
	public static String formatCount(Integer count) {
		DecimalFormat df = new DecimalFormat(",###,##0");
		return df.format(count);
	}

	/**
	 * 
	 * @Title: arrToStr
	 * @Description:将数组转成字符串
	 * @param arr
	 * @return
	 */
	public static <T> String arrToStr(T[] arr) {
		return arrToStr(arr, ",");
	}

	/**
	 * 
	 * @Title: arrToStr
	 * @Description:将数组转成字符串
	 * @param arr
	 * @param split
	 * @return
	 */
	public static <T> String arrToStr(T[] arr, String split) {
		if (arr == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (T t : arr) {
			sb.append(t.toString()).append(split);
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 
	 * @Title: appendWithUnSafe
	 * @Description: 非线程安全下的字符串拼接
	 * @return
	 */
	public static String appendWithUnSafe(Object... objs) {
		if (objs == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (Object obj : objs) {
			builder.append(obj);
		}
		return builder.toString();
	}

	/**
	 * 
	 * @Title: appendWithSafe
	 * @Description: 线程安全下的字符串拼接
	 * @return
	 */
	public static String appendWithSafe(Object... objs) {
		if (objs == null) {
			return null;
		}
		StringBuffer builder = new StringBuffer();
		for (Object obj : objs) {
			builder.append(obj);
		}
		return builder.toString();
	}

}
