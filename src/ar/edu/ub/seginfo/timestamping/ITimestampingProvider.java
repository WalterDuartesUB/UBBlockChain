package ar.edu.ub.seginfo.timestamping;

public interface ITimestampingProvider {
	long	getTimeStamp( String hashToStamp );
}
