package ar.edu.ub.seginfo.cipher.hashgenerator;

import java.security.MessageDigest;

public class HashedData implements IHashedData {
	private String        hash;
	private MessageDigest digest;
	private byte[] hashByte;
	
	public HashedData(String hash, MessageDigest digest) {
		this.setHash(hash);
		this.setDigest(digest);		
	}

	public HashedData(String upperCase, MessageDigest md, byte[] digest2) {
		this( upperCase, md );		
		this.setHashByte(digest2);
	}

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

	public byte[] getHashByte() {
		return hashByte;
	}

	public void setHashByte(byte[] hashByte) {
		this.hashByte = hashByte;
	}

}
