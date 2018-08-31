package ar.edu.ub.seginfo.cipher;

public interface IBidirectionalCipher{
	String encrypt	( String data );
	String decrypt( String hash );
}
