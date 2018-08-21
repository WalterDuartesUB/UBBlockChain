package ar.edu.ub.seginfo.model;

import ar.edu.ub.seginfo.cipher.Cipher;

public class Block {
	private int index;
	private long timestamping;
	private String hash;
	private String previousHash;	
	private String data;	
	private int nonce;	
	private Cipher cipher;
	
	public Block(int index, String previousHash, String data, Cipher cipher){		
		this.setIndex(index);
		this.setPreviousHash(previousHash);
		this.setData(data);		
		this.setTimestamping( System.currentTimeMillis() );
		this.setCipher(cipher);
		
		this.mineBlock( 4 );
	}

	private void mineBlock( int dificulty ) {		
		
		this.setNonce( 0 );
		
		String h = this.getCipher().generateHash( this.getDataToGenerateHash() );
		
		//Si encuentro algo que no sea un cero en los primeros x caracteres, es invalido
		while( !this.isValidHash( h.substring(0, dificulty + 1 ) ) )
		{
			this.increaseNonce();
			
			//Recalculo el hash del bloque
			h = this.getCipher().generateHash( this.getDataToGenerateHash() );
		}
		
		//Asigno el hash que genere como el hash del bloque
		this.setHash( h );
	}

	private boolean isValidHash(String h) {
		return h.indexOf('0') == -1;
	}

	private void increaseNonce() {
		this.setNonce( this.getNonce() + 1 );
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
		
	private String getDataToGenerateHash() {
		return String.format("%d%s%d%s%d", this.getIndex(), this.getData(), this.getTimestamping(), this.getPreviousHash(), this.getNonce() );
	}

	private int getNonce() {
		return nonce;
	}

	private void setNonce(int nonce) {
		this.nonce = nonce;
	}
	
}
