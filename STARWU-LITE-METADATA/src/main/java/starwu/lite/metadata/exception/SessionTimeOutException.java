package starwu.lite.metadata.exception;

/**
 * 
 * @ClassName: SessionTimeOutException
 * @Description: 自定义session过期异常
 * @author Lone Wolf
 * @date 2023年11月25日
 */
public class SessionTimeOutException extends RuntimeException {

	private static final long serialVersionUID = 7578063655955557647L;

	public SessionTimeOutException() {

	}

	public SessionTimeOutException(String message) {
		super(message);
	}

}
