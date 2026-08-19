package starwu.lite.util;

import lombok.SneakyThrows;

import java.io.*;

/**
 * 
 * @ClassName: SerializeUtil
 * @Description: 序列化工具类
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class SerializeUtil {

	/**
	 * 
	 * @Title: serialize
	 * @Description: 将对象序列化成字符串
	 * @param t
	 * @return
	 */
	@SneakyThrows
	public static <T extends Serializable> String serialize(T t) {
		if (t == null) {
			return null;
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = null;
		String string = null;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(t);
			string = byteArrayOutputStream.toString("ISO-8859-1");
		} finally {
			byteArrayOutputStream.close();
			objectOutputStream.close();
		}
		return string;
	}

	/**
	 * 
	 * @Title: serializeToObject
	 * @Description: 将字符串反序列化为对象
	 * @param ser
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@SneakyThrows
	public static <T extends Serializable> T serializeToObject(String ser) {
		if (ser == null) {
			return null;
		}
		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		T t = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(ser.getBytes("ISO-8859-1"));
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			t = (T) objectInputStream.readObject();
		} finally {
			objectInputStream.close();
			byteArrayInputStream.close();
		}
		return t;
	}

}
