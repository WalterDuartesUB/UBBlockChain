package ar.edu.ub.seginfo.timestamping;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashedData;

public class TimestampingProviderSystem implements ITimestampingProvider {
	@Override
	public IStampedHashedData stamp(IHashedData data) {
		return new StampedHashedData(data, System.currentTimeMillis());
	}

}
