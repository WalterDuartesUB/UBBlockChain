package ar.edu.ub.seginfo.timestamping;

import java.time.LocalDateTime;

public class TimestampResponse implements ITimestampResponse {
	private LocalDateTime signingTime;
	private String encodedToken;

	@Override
	public LocalDateTime getSigningTime() {
		return this.signingTime;
	}

	@Override
	public String getEncodedToken() {
		return this.encodedToken;
	}

	public TimestampResponse(LocalDateTime signingTime, String encodedToken) {
		this.setEncodedToken(encodedToken);
		this.setSigningTime(signingTime);
	}

	private void setEncodedToken(String encodedToken) {
		this.encodedToken = encodedToken;
	}

	private void setSigningTime(LocalDateTime signingTime) {
		this.signingTime = signingTime;
	}

}
