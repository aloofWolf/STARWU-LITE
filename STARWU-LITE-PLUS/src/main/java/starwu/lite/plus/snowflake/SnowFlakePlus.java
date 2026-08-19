package starwu.lite.plus.snowflake;

import org.springframework.stereotype.Component;
import starwu.lite.plus.threadLocal.ThreadLocalPlus;
import starwu.lite.util.DateUtil;
import starwu.lite.util.EnvironmentUtil;
import starwu.lite.util.StringUtil;

import java.util.Date;

/**
 * 
 * @ClassName: SnowFlakeUtil
 * @Description: 雪花算法
 * @author Lone Wolf
 * @date 2019年9月10日
 */
@Component
public class SnowFlakePlus {

	/**
	 * 
	 * @Title: getSerialNumber
	 * @Description: 生成序列号
	 * @param prefix
	 * @return
	 */
	public static String getSerialNumber(String prefix) {
		return getSerialNumber(prefix, SnowFlakeConfig.getDefaultConfig());

	}

	/**
	 * 
	 * @Title: getSerialNumber
	 * @Description: 生成序列号
	 * @param prefix
	 * @param snowFlakeConfig
	 * @return
	 */
	public static String getSerialNumber(String prefix, SnowFlakeConfig snowFlakeConfig) {
		String dateStr = getDateStr(snowFlakeConfig);
		String containerIdStr = getContainerIdStr(snowFlakeConfig);
		String threadIdStr = getThreadIdStr(snowFlakeConfig);
		String serialNumberStr = getSerialNumberStr(prefix, snowFlakeConfig);
		return append(prefix, dateStr, containerIdStr, threadIdStr, serialNumberStr);

	}

	/**
	 * 
	 * @Title: getDateStr
	 * @Description: 获取日期字符串
	 * @param snowFlakeConfig
	 * @return
	 */
	protected static String getDateStr(SnowFlakeConfig snowFlakeConfig) {
		return DateUtil.getDateString(new Date(), snowFlakeConfig.getDateFormat());
	}

	/**
	 * 
	 * @Title: getContainerIdStr
	 * @Description: 获取容器id字符串
	 * @param snowFlakeConfig
	 * @return
	 */
	protected static String getContainerIdStr(SnowFlakeConfig snowFlakeConfig) {
		return intValueConvertToStr(EnvironmentUtil.getContainerId(), snowFlakeConfig.getContainerIdLength());
	}

	/**
	 * 
	 * @Title: getThreadIdStr
	 * @Description: 获取线程id字符串
	 * @param snowFlakeConfig
	 * @return
	 */
	protected static String getThreadIdStr(SnowFlakeConfig snowFlakeConfig) {
		int treadId = (int) Thread.currentThread().getId();
		return intValueConvertToStr(treadId, snowFlakeConfig.getThreadIdLength());
	}

	/**
	 * 
	 * @Title: getSerialNumberStr
	 * @Description: 获取序列号
	 * @param prefix
	 * @param snowFlakeConfig
	 * @return
	 */
	protected static String getSerialNumberStr(String prefix, SnowFlakeConfig snowFlakeConfig) {
		Integer currSerialNumber = ThreadLocalPlus.get(prefix,false);
		if (currSerialNumber == null) {
			currSerialNumber = snowFlakeConfig.getSerialNumberMinValue();
		}
		Integer nextSerialNumber = currSerialNumber + 1;
		if (nextSerialNumber > snowFlakeConfig.getSerialNumberMaxValue()) {
			nextSerialNumber = snowFlakeConfig.getSerialNumberMinValue();
		}
		ThreadLocalPlus.put(prefix,false);
		return intValueConvertToStr(currSerialNumber, snowFlakeConfig.getSerialNumberLength());

	}

	/**
	 * 
	 * @Title: append
	 * @Description: 对各个元素进行拼接
	 * @param prefix
	 * @param DateStr
	 * @param containerIdStr
	 * @param threadIdStr
	 * @param serialNumberStr
	 * @return
	 */
	protected static String append(String prefix, String DateStr, String containerIdStr, String threadIdStr,
			String serialNumberStr) {
		return StringUtil.appendWithUnSafe(prefix, DateStr, containerIdStr, threadIdStr, serialNumberStr);
	}

	/**
	 * 
	 * @Title: intValueConvertToStr
	 * @Description: 生成特定长度的字符串
	 * @param value
	 * @param length
	 * @return
	 */
	protected static String intValueConvertToStr(int value, int length) {
		String valueStr = Integer.toString(value);
		if (valueStr.length() < length) {
			return StringUtil.flushChar('0', length, valueStr, 0);
		}
		if (valueStr.length() > length) {
			return valueStr.substring(length);
		}
		return valueStr;
	}
}
