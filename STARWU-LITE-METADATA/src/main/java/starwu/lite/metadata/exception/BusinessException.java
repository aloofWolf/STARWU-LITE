package starwu.lite.metadata.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7578063655955557647L;

	public BusinessException() {

	}

	public BusinessException(String message) {
		super(message);
	}

}
