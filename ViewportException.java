package dmeyers.engine;

public class ViewportException extends Exception {

	public ViewportException() {
	}

	public ViewportException(String message) {
		super(message);
	}

	public ViewportException(Throwable cause) {
		super(cause);
	}

	public ViewportException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewportException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
