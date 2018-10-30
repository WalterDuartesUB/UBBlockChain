package ar.edu.ub.seginfo.cipher.bidirectionalcipher;

public class BidirectionalCipherException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BidirectionalCipherException(String message, Exception e) {
		super(String.format("%s. %s", message, e.getMessage() ) );
	}
}
