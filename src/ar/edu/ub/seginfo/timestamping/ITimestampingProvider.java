package ar.edu.ub.seginfo.timestamping;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashedData;
import ar.edu.ub.seginfo.exception.TimestampingException;

public interface ITimestampingProvider {
//	long	getTimeStamp( String hashToStamp );
	public  IStampedHashedData stamp(IHashedData data) throws TimestampingException;
}
