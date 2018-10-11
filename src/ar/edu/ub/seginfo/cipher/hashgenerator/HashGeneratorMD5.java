package ar.edu.ub.seginfo.cipher.hashgenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class HashGeneratorMD5 implements IHashGenerator {

	@Override
	public String generateHash(String data) {
		return this.generateHash( data.getBytes() );
	}

	@Override
	public String generateHash(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
		    
			md.update(data);	    
		    
		    return DatatypeConverter.printHexBinary( md.digest() ).toUpperCase();
		    
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public IHashedData hash(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

}
