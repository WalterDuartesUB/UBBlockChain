package ar.edu.ub.seginfo.cipher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class CipherDummy implements ICipher {

	@Override
	public String encrypt(String data) {
		return this.encrypt( data.getBytes() );
	}

	@Override
	public String encrypt(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
		    
			md.update(data);	    
		    
		    return DatatypeConverter.printHexBinary( md.digest() ).toUpperCase();
		    
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
