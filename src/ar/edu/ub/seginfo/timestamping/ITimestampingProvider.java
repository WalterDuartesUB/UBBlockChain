package ar.edu.ub.seginfo.timestamping;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashedData;
import ar.edu.ub.seginfo.exception.TimestampingException;

public interface ITimestampingProvider {
	public IStampedHashedData stamp(IHashedData data) throws TimestampingException;
}
