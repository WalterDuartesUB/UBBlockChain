package ar.edu.ub.seginfo.model;

public class Block implements IBlock {
	private String 	previousHash;
	private String 	data;
	private long	timeStamp;
	private String	blockHash;
	
	public Block(String previousHash, String data, long timeStamp) {		
		this.setPreviousHash(previousHash);
		this.setData(data);
		this.setTimeStamp(timeStamp);
		
		this.generateBlockHash();
	}

	private void generateBlockHash() {
		this.setBlockHash( this.getBlockData() );
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

}
