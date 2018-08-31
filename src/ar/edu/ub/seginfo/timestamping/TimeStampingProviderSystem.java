package ar.edu.ub.seginfo.timestamping;

public class TimeStampingProviderSystem implements ITimeStampingProvider {

	@Override
	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

}
