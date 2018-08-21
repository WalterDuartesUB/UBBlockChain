package ar.edu.ub.seginfo.model;

import ar.edu.ub.seginfo.cipher.Cipher;

public class Block {
	private int index;
	private long timestamping;
	private String hash;
	private String previousHash;	
	private String data;
	
	private Cipher cipher;
	
	public Block(int index, String previousHash, String data, Cipher cipher){		
		this.setIndex(index);
		this.setPreviousHash(previousHash);
		this.setData(data);		
		this.setTimestamping( System.currentTimeMillis() );
		this.setCipher(cipher);
		
		this.calculateHash();
	}

	private void calculateHash() {		
		this.setHash( this.getCipher().generateHash( this.getHashData() ) );		
	}

	public int getIndex() {
		return index;
	}

	private void setIndex(int index) {
		this.index = index;
	}

	public long getTimestamping() {
		return timestamping;
	}

	private void setTimestamping(long timestamping) {
		this.timestamping = timestamping;
	}

	public String getHash() {
		return hash;
	}

	private void setHash(String hash) {
		this.hash = hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	private void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String getData() {
		return data;
	}

	private void setData(String data) {
		this.data = data;
	}

	public Cipher getCipher() {
		return cipher;
	}

	private void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}
		
	private String getHashData() {
		return String.format("%d%s%d%s", this.getIndex(), this.getData(), this.getTimestamping(), this.getPreviousHash() );
	}
	
}
