package starwu.lite.metadata.exception;

/**
 * 
 * @ClassName: BusinessException
 * @Description: 自定义异常，在抛出此异常时，需要将具体异常信息返回给用户
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7578063655955557647L;

	public BusinessException() {


	}

	public BusinessException(String message) {
		super(message);
	}

}
