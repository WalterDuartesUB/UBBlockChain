package ar.edu.ub.seginfo.exception;

import java.security.NoSuchAlgorithmException;

public class HashGeneratorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HashGeneratorException(String message, NoSuchAlgorithmException e) {
		super(String.format("%s. %s", message, e.getMessage()));
	}
}
