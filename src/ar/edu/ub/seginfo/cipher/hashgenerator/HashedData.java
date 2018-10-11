package ar.edu.ub.seginfo.cipher.hashgenerator;

import java.security.MessageDigest;

public class HashedData implements IHashedData {
	private String        hash;
	private MessageDigest digest;
	
	@Override
	public String getHash() {
		return this.hash;
	}

	@Override
	public String getDigestAlgorithm() {
		return this.getDigest().getAlgorithm();
	}

	private void setHash(String hash) {
		this.hash = hash;
	}

	private MessageDigest getDigest() {
		return digest;
	}

	private void setDigest(MessageDigest digest) {
		this.digest = digest;
	}

}
