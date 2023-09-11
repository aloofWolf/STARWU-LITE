package starwu.lite.metadata.exception;

public class SqlException extends RuntimeException {

	private static final long serialVersionUID = 1635581247042430649L;

	public SqlException() {

	}

	public SqlException(String message) {
		super(message);
	}

}
