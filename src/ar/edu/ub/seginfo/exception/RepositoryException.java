package ar.edu.ub.seginfo.exception;

public class RepositoryException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RepositoryException(String message, Exception e) {
		super( String.format("%s %s", message, e.getMessage() ) );
	}
}
