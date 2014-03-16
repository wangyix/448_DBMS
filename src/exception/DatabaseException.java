package exception;

public class DatabaseException extends Exception {

	private static final long serialVersionUID = 4207535379126073589L;

	public DatabaseException(String message) {
		super(message);
	}
}
