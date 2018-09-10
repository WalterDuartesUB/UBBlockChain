package ar.edu.ub.seginfo.timestamping;

public class TimeStampingProviderSystem implements ITimeStampingProvider {

	@Override
	public long getTimeStamp( String hashToStamp ) {
		return System.currentTimeMillis();
	}

}
