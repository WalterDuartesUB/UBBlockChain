package ar.edu.ub.seginfo.cipher.bidirectionalcipher;

public interface IBidirectionalCipher{
	String encrypt	( String data );
	String decrypt( String hash );
}
