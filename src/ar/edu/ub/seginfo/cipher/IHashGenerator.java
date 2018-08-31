package ar.edu.ub.seginfo.cipher;

public interface IHashGenerator {
	String generateHash	( String data );
	String generateHash	( byte[] data );
}
