package ar.edu.ub.seginfo.model;

import ar.edu.ub.seginfo.cipher.IBidirectionalCipher;

public class Block implements IBlockFields {
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

	public Block(IBlock b, IBidirectionalCipher dataCipher) {
		this.setDataCipher(dataCipher);
		this.setPreviousHash( b.getPreviousHash() );		
		this.setBlockHash( b.getHash() );
		
		//Desencripto la data
		String blockData = this.getDataCipher().getData( this.getHash() );
		
		this.setData( blockData.substring(0, 32) );		
		blockData = blockData.substring(32);
		
		//Quito el prev
//		blockData = blockData.substring( this.getPreviousHash().length() );
		
		//Pongo el timestamp
		this.setTimeStamp( Long.parseLong( blockData ) );
		
	}

	private void generateBlockHash() {
		this.setBlockHash( this.getDataCipher().generateHash( this.getBlockData() ) );
	}

	private String getBlockData() {
//		return String.format("%s%s%d", this.getData(), this.getPreviousHash(), this.getTimeStamp() );
		return String.format("%s%d", this.getData(), this.getTimeStamp() );
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

	@Override
	public String getData() {
		return data;
	}

	private void setData(String data) {
		this.data = data;
	}

	@Override
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

	@Override
	public boolean hasTheSameDataThan(IBlock block) {
		String data = this.getDataCipher().getData( block.getHash() );
		String unciphedData = data.substring(0, this.getData().length() );
		return unciphedData.compareToIgnoreCase( this.getData() ) == 0;
	}

}
