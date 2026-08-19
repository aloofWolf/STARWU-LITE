package starwu.lite.metadata.exception;

/**
 * 
 * @ClassName: ParamValidationException
 * @Description: 自定义参数校验异常
 * @author Lone Wolf
 * @date 2023年11月25日
 */
public class ParamValidationException extends RuntimeException {

	private static final long serialVersionUID = 7578063655955557647L;

	public ParamValidationException() {

	}

	public ParamValidationException(String message) {
		super(message);
	}

}
