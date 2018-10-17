package ar.edu.ub.seginfo.cipher.hashgenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class HashGeneratorMD5 implements IHashGenerator {

	@Override
	public IHashedData hash(byte[] data) {
	
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
		    
			md.reset();	    
		    
		    return new HashedData( DatatypeConverter.printHexBinary( md.digest( data ) ).toUpperCase(), md, md.digest( data ) );
		    
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;		
	}

}
