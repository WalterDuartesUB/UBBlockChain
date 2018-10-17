package ar.edu.ub.seginfo.timestamping;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashedData;

public interface ITimestampingProvider {
//	long	getTimeStamp( String hashToStamp );
	public  IStampedHashedData stamp(IHashedData data);
}
