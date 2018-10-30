package ar.edu.ub.seginfo.cipher.hashgenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ar.edu.ub.seginfo.exception.HashGeneratorException;

public class HashGeneratorMD5 implements IHashGenerator {

	@Override
	public IHashedData hash(byte[] data) throws HashGeneratorException {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.reset();

			return new HashedData(md.digest(data), md);

		} catch (NoSuchAlgorithmException e) {
			throw new HashGeneratorException("Ocurrio un error al tratar de generar el fingerprint. ", e);
		}
	}

}
