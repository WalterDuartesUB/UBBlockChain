package ar.edu.ub.seginfo.cipher;

public interface ICipher {
	String generateHash	( String data );
	String generateHash	( byte[] data );
}
