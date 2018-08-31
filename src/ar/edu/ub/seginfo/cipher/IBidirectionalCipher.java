package ar.edu.ub.seginfo.cipher;

public interface IBidirectionalCipher extends ICipher {
	String decrypt( String hash );
}
