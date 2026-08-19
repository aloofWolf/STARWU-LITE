package starwu.lite.plus.snowflake;

import lombok.Getter;
import starwu.lite.util.DateUtil;

/**
 * 
 * @ClassName: SnowFlakeConfig
 * @Description: 雪花算法配置
 * @author Lone Wolf
 * @date 2023年11月7日
 */
@Getter
public class SnowFlakeConfig {

	private String dateFormat; // 日期格式

	private int containerIdLength; // 容器id长度

	private int threadIdLength; // 线程id长度

	private int serialNumberMinValue; // 序列号最小值

	private int serialNumberMaxValue; // 序列号最大值

	private int serialNumberLength; // 序列号长度

	public SnowFlakeConfig(String dateFormat, int containerIdLength, int threadIdLength, int serialNumberMinValue,
                           int serialNumberMaxValue, int serialNumberLength) {
		super();
		this.dateFormat = dateFormat;
		this.containerIdLength = containerIdLength;
		this.threadIdLength = threadIdLength;
		this.serialNumberMinValue = serialNumberMinValue;
		this.serialNumberMaxValue = serialNumberMaxValue;
		this.serialNumberLength = serialNumberLength;
	}

	/**
	 * 雪花算法的默认配置
	 */
	private static final SnowFlakeConfig defaultConfig = new SnowFlakeConfig(DateUtil.DEFAULT_DATETIMEMSPATTERN_SIMP, 3,
			5, 0, 999999, 6);

	/**
	 * 
	 * @Title: getDefaultConfig
	 * @Description: 获取雪花算法的默认配置
	 * @return
	 */
	public static SnowFlakeConfig getDefaultConfig() {
		return defaultConfig;
	}

}
