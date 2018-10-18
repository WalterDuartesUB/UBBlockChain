package ar.edu.ub.seginfo.timestamping;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashedData;

public class StampedHashedData implements IStampedHashedData {

	private String  hash;
	private long    timestamp;
	
	public StampedHashedData(IHashedData data, long timestamp) {
		this.setHash( data.getHashAsString() );
		this.setTimestamp(timestamp);
	}

	@Override
	public String getHash() {
		return this.hash;
	}

	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
