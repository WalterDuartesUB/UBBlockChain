package ar.edu.ub.seginfo.model;

public class BlockChainAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BlockChainAlreadyExistsException(String msg ) {
		super( msg );
	}
	

}