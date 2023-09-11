package starwu.lite.metadata.exception;

public class SessionTimeOutException extends RuntimeException {

	private static final long serialVersionUID = 7578063655955557647L;

	public SessionTimeOutException() {

	}

	public SessionTimeOutException(String message) {
		super(message);
	}

}
