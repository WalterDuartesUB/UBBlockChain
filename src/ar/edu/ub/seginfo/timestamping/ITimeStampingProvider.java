package ar.edu.ub.seginfo.timestamping;

public interface ITimeStampingProvider {
	long	getTimeStamp( String hashToStamp );
}
