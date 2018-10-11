package ar.edu.ub.seginfo.cipher.hashgenerator;

import ar.edu.ub.seginfo.timestamping.IHashedData;

public interface IHashGenerator {
	String generateHash	( String data );
	String generateHash	( byte[] data );
	
	IHashedData hash ( byte[] data );
}
