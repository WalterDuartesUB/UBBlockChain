package ar.edu.ub.seginfo.timestamping;

public class StampedHashedData implements IStampedHashedData {

	private String  hash;
	private long    timestamp;
	
	public StampedHashedData(IHashedData data, long timestamp) {
		this.setHash( data.getHash() );
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
