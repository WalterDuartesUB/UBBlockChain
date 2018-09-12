package ar.edu.ub.seginfo.cipher.bidirectionalcipher;

public class CipherDummyBidirectional implements IBidirectionalCipher {

	@Override
	public String encrypt(String data) {
		return data.toUpperCase();
	}

	@Override
	public String decrypt(String hash) {
		return hash.toLowerCase();
	}

}
