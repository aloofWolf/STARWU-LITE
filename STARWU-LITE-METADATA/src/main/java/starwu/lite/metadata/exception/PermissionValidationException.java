package starwu.lite.metadata.exception;

public class PermissionValidationException extends RuntimeException {

	private static final long serialVersionUID = 7578063655955557647L;

	public PermissionValidationException() {

	}

	public PermissionValidationException(String message) {
		super(message);
	}

}