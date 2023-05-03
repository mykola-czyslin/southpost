package ua.southpost.resource.backup.web;

/**
 * The generic web exception descriptor
 * Created by mykola on 06.11.16.
 */
public class GenericWebException extends RuntimeException {

	public GenericWebException() {
	}

	public GenericWebException(String message) {
		super(message);
	}

	public GenericWebException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenericWebException(Throwable cause) {
		super(cause);
	}

	public GenericWebException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
