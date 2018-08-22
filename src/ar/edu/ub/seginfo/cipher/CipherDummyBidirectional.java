package ar.edu.ub.seginfo.cipher;

public class CipherDummyBidirectional implements IBidirectionalCipher {

	@Override
	public String generateHash(String data) {		
		return new StringBuilder( data ).reverse().toString();
	}

	@Override
	public String getData(String hash) {
		return new StringBuilder( hash ).reverse().toString();
	}

	@Override
	public String generateHash(byte[] data) {
		return null;
	}

}
