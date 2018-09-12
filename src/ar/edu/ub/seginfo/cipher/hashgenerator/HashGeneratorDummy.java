package ar.edu.ub.seginfo.cipher.hashgenerator;

import java.nio.charset.StandardCharsets;

public class HashGeneratorDummy implements IHashGenerator {

	@Override
	public String generateHash(String data) {
		return data;
	}

	@Override
	public String generateHash(byte[] data) {
		return new String(data, StandardCharsets.UTF_8);
	}



}
