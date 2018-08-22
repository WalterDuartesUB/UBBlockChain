package ar.edu.ub.seginfo.model;

import ar.edu.ub.seginfo.cipher.IBidirectionalCipher;

public class Block implements IBlock {
	private String 	previousHash;
	private String 	data;
	private long	timeStamp;
	private String	blockHash;
	private IBidirectionalCipher dataCipher;
	
	public Block(String previousHash, String data, long timeStamp, IBidirectionalCipher dataCipher) {		
		this.setPreviousHash(previousHash);
		this.setData(data);
		this.setTimeStamp(timeStamp);
		this.setDataCipher(dataCipher);
		
		this.generateBlockHash();
	}

	private void generateBlockHash() {
		this.setBlockHash( this.getDataCipher().generateHash( this.getBlockData() ) );
	}

	private String getBlockData() {
		return String.format("%s%s%d", this.getData(), this.getPreviousHash(), this.getTimeStamp() );
	}

	@Override
	public String getPreviousHash() { 
		return this.previousHash;
	}

	@Override
	public String getHash() {
		return blockHash;
	}

	@Override
	public boolean isValidBlock() {
		return false;
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

	public long getTimeStamp() {
		return timeStamp;
	}

	private void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	private void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}

	private IBidirectionalCipher getDataCipher() {
		return dataCipher;
	}

	private void setDataCipher(IBidirectionalCipher dataCipher) {
		this.dataCipher = dataCipher;
	}

}
