package ar.edu.ub.seginfo.cipher.hashgenerator;

import ar.edu.ub.seginfo.exception.HashGeneratorException;

public interface IHashGenerator {
	IHashedData hash ( byte[] data ) throws HashGeneratorException;
}
