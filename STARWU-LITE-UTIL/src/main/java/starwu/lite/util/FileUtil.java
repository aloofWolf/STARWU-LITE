package starwu.lite.util;

import lombok.SneakyThrows;

import java.io.FileWriter;

/**
 * 
 * @ClassName: FileUtil
 * @Description: file工具类
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class FileUtil {


	/**
	 * 
	 * @Title: getDirByFilePath
	 * @Description:根据文件路径获取文件所在文件夹
	 * @param filePath
	 * @return
	 */
	public static String getDirByFilePath(String filePath) {
		if (filePath == null) {
			return "";
		}
		String replaceFilePath = filePath.replaceAll("\\\\", "/");
		if (replaceFilePath.lastIndexOf("/") == -1) {
			return "";
		}
		return replaceFilePath.substring(0, replaceFilePath.lastIndexOf("/"));
	}

	/**
	 * 
	 * @Title: getFileNameByFilePath
	 * @Description:根据文件路径获取文件名
	 * @param filePath
	 * @return
	 */
	public static String getFileNameByFilePath(String filePath) {
		if (filePath == null) {
			return "";
		}
		String replaceFilePath = filePath.replaceAll("\\\\", "/");
		if (replaceFilePath.lastIndexOf("/") == -1) {
			return filePath;
		}
		return replaceFilePath.substring(replaceFilePath.lastIndexOf("/") + 1, replaceFilePath.length());
	}

	/**
	 * 
	 * @Title: strWriteToFile
	 * @Description:将字符串写入文件
	 * @param str
	 * @param path
	 */
	@SneakyThrows
	public static void strWriteToFile(String str, String path) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(path);
			writer.write(str);
			writer.flush();
		} finally {
			writer.close();
		}
	}
}
