package ar.edu.ub.seginfo.cipher;

public class CipherDummy implements ICipher {

	@Override
	public String generateHash(String data) {
		return data.toLowerCase();
	}

}
