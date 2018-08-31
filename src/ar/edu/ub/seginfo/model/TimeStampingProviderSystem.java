package ar.edu.ub.seginfo.model;

public class TimeStampingProviderSystem implements ITimeStampingProvider {

	@Override
	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

}
