package starwu.lite.metadata.exception;

/**
 * 
 * @ClassName: PermissionValidationException
 * @Description: 自定义权限校验异常
 * @author Lone Wolf
 * @date 2023年11月25日
 */
public class PermissionValidationException extends RuntimeException {

	private static final long serialVersionUID = 7578063655955557647L;

	public PermissionValidationException() {

	}

	public PermissionValidationException(String message) {
		super(message);
	}

}