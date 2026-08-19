package starwu.lite.metadata.enumPlus;

public interface EnumApi<T> {

	/**
	 * 枚举操作的异常信息
	 */
	public String idNoExistExceptionMessage = "枚举%s不存在此id:%d";

	/**
	 * 
	 * @Title: getId
	 * @Description: 获取枚举id
	 * @return
	 */
	public T getId();


}
