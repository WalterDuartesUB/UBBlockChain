package ar.edu.ub.seginfo.cipher;

public class CipherDummy implements ICipher, IBidirectionalCipher {

	@Override
	public String generateHash(String data) {
		return data;
	}

	@Override
	public String getData(String hash) {
		return hash;
	}

}
