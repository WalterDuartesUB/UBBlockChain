package ar.edu.ub.seginfo.cipher.hashgenerator;

public interface IHashGenerator {
	String generateHash	( String data );
	String generateHash	( byte[] data );
	
	IHashedData hash ( byte[] data );
}
