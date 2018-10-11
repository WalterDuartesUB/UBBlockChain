package ar.edu.ub.seginfo.timestamping;

public class TimestampingProviderSystem implements ITimestampingProvider {

	@Override
	public long getTimeStamp( String hashToStamp ) {
		return System.currentTimeMillis();
	}

	@Override
	public IStampedHashedData stamp(IHashedData data) {
		return null;
	}

}
