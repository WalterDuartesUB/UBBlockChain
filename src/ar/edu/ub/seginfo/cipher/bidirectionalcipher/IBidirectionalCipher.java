package ar.edu.ub.seginfo.cipher.bidirectionalcipher;

import ar.edu.ub.seginfo.exception.BidirectionalCipherException;

public interface IBidirectionalCipher {
	String encrypt(String data) throws BidirectionalCipherException;

	String decrypt(String hash) throws BidirectionalCipherException;
}
