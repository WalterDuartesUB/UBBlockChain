package ar.edu.ub.seginfo.cipher.bidirectionalcipher;

public interface IBidirectionalCipher {
	String encrypt(String data) throws BidirectionalCipherException;

	String decrypt(String hash) throws BidirectionalCipherException;
}
