package ar.edu.ub.seginfo.timestamping;

import java.time.LocalDateTime;

public interface ITimestampResponse {
	public LocalDateTime 	getSigningTime();
	public String 			getEncodedToken();
}
