package ar.edu.ub.seginfo.model;

public class Block {
	private String timestamping;
	private String data;
	
	public Block(String timestamping, String data) {
		this.setTimestamping(timestamping);
		this.setData(data);
	}

	public String getData() {
		return data;
	}

	private void setData(String data) {
		this.data = data;
	}

	public String getTimestamping() {
		return timestamping;
	}

	private void setTimestamping(String timestamping) {
		this.timestamping = timestamping;
	}
	
	
}
