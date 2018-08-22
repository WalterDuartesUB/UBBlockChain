package ar.edu.ub.seginfo.cipher;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherDummyBidirectional implements IBidirectionalCipher {

	@Override
	public String generateHash(String data) {		
		return this.generateHash(data.getBytes());
	}

	@Override
	public String getData(String hash) {
		return this.decrypt( this.getKey(), this.getInitVector(), hash);
	}

	private String getKey() {
		return "Bar12345Bar12345";
	}

	@Override
	public String generateHash(byte[] data) {
		return this.encrypt( this.getKey(), this.getInitVector(), data);
	}
	
    private String getInitVector() {
		return "RandomInitVector";
	}

	private String encrypt(String key, String initVector, byte[] value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value);
            
            return Base64.getEncoder().encodeToString( encrypted );
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }	
}
