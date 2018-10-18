package ar.edu.ub.seginfo.cipher.hashgenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGeneratorMD5 implements IHashGenerator {

	@Override
	public IHashedData hash(byte[] data) {
	
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
		    
			md.reset();	    
		    
		    return new HashedData( md.digest( data ), md );
		    
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;		
	}

}
