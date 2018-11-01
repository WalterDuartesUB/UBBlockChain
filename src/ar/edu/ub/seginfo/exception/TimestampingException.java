package ar.edu.ub.seginfo.exception;

public class TimestampingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimestampingException(String message, Exception e) {
		super(String.format("%s. %s", message, e.getMessage()));
	}
}
