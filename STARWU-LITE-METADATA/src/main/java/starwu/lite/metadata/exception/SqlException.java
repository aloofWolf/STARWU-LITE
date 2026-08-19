package starwu.lite.metadata.exception;

/**
 * 
 * @ClassName: SqlException
 * @Description: sql异常
 * @author Lone Wolf
 * @date 2019年9月10日
 */
public class SqlException extends RuntimeException {

	private static final long serialVersionUID = 1635581247042430649L;

	public SqlException() {

	}

	public SqlException(String message) {
		super(message);
	}

}
