package dmeyers.engine;

public class AStarException extends Exception {

	public AStarException() {
	}

	public AStarException(String message) {
		super(message);
	}

	public AStarException(Throwable cause) {
		super(cause);
	}

	public AStarException(String message, Throwable cause) {
		super(message, cause);
	}

	public AStarException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
