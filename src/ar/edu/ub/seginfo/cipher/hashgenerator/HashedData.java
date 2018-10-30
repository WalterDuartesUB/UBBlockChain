package ar.edu.ub.seginfo.cipher.hashgenerator;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class HashedData implements IHashedData {
	private byte[] hash;
	private MessageDigest digest;
	private String hashAsString;

	public HashedData(byte[] hash, MessageDigest digest) {
		this.setHash(hash);
		this.setDigest(digest);
	}

	@Override
	public String getHashAsString() {
		return this.hashAsString;
	}

	@Override
	public String getDigestAlgorithm() {
		return this.getDigest().getAlgorithm();
	}

	private void setHash(byte[] hash) {
		this.hash = hash;

		this.setHashAsString(DatatypeConverter.printHexBinary(this.getHash()).toUpperCase());
	}

	private MessageDigest getDigest() {
		return digest;
	}

	private void setDigest(MessageDigest digest) {
		this.digest = digest;
	}

	@Override
	public byte[] getHash() {
		return this.hash;
	}

	private void setHashAsString(String hashAsString) {
		this.hashAsString = hashAsString;
	}

}
