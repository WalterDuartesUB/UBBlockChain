package ar.edu.ub.seginfo.model;

import ar.edu.ub.seginfo.cipher.bidirectionalcipher.IBidirectionalCipher;
import ar.edu.ub.seginfo.exception.BlockInvalidFingerPrintException;
import ar.edu.ub.seginfo.timestamping.IStampedHashedData;

public class Block implements IBlockFields {
	private String 	previousHash;
	private String 	data;
	private long	timeStamp;
	private String	blockHash;
	private IBidirectionalCipher dataCipher;
	
	protected Block(String previousHash, String data, long timeStamp, IBidirectionalCipher dataCipher) {		
		this.setPreviousHash(previousHash);
		this.setData(data);
		this.setTimeStamp(timeStamp);
		this.setDataCipher(dataCipher);
		
		this.generateBlockHash();
	}
	
	public Block(String previousHash, IStampedHashedData stampedData, IBidirectionalCipher dataCipher) {
		this( previousHash, stampedData.getHash(), stampedData.getTimestamp(), dataCipher );
	}
	
	public static IBlockFields createBlock(IBlock b, IBidirectionalCipher dataCipher) {		
		String blockData = dataCipher.decrypt( b.getHash() );
		
		String documentFingerPrint = blockData.substring(0, 32);
		
		//Si no es un hexa valido, salgo
		if( !documentFingerPrint.matches("[0-9a-fA-F]+") )
			throw new BlockInvalidFingerPrintException();		
						
		return new Block( b.getPreviousHash(), documentFingerPrint, Long.parseLong( blockData.substring(32) ), dataCipher );
	}
	
/*
	public Block(IBlock b, IBidirectionalCipher dataCipher) {
		this.setDataCipher(dataCipher);
		this.setPreviousHash( b.getPreviousHash() );		
		this.setBlockHash( b.getHash() );
		
		//Desencripto la data
		String blockData = this.getDataCipher().decrypt( this.getHash() );
		
		//Guardo la data
		this.setData( blockData.substring(0, 32) );				
		
		//Pongo el timestamp con el restante de la data
		this.setTimeStamp( Long.parseLong( blockData.substring(32) ) );
		
	}
*/
	private void generateBlockHash() {
		this.setBlockHash( this.getDataCipher().encrypt( this.getBlockData() ) );
	}

	private String getBlockData() {
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
		String data = this.getDataCipher().decrypt( block.getHash() );
		String unciphedData = data.substring(0, this.getData().length() );
		return unciphedData.compareToIgnoreCase( this.getData() ) == 0;
	}

}
