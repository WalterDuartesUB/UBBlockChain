package ar.edu.ub.seginfo.cipher.bidirectionalcipher;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherAES implements IBidirectionalCipher {

	@Override
	public String encrypt(String data) throws BidirectionalCipherException {
		return this.encrypt(data.getBytes());
	}

	@Override
	public String decrypt(String hash) throws BidirectionalCipherException {
		return this.decrypt(this.getKey(), this.getInitVector(), hash);
	}

	private String encrypt(byte[] data) throws BidirectionalCipherException {
		return this.encrypt(this.getKey(), this.getInitVector(), data);
	}

	private String getKey() {
		return "Bar12345Bar12345";
	}

	private String getInitVector() {
		return "RandomInitVector";
	}

	private String encrypt(String key, String initVector, byte[] value) throws BidirectionalCipherException {
		try {
			IvParameterSpec iv = getInitVectorParam(initVector);
			SecretKeySpec skeySpec = getSecretKeySpec(key);

			Cipher cipher = getCipherAlgoritm();
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value);

			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception ex) {
			throw new BidirectionalCipherException("Ocurrio un erro al tratar de encriptar", ex);
		}
	}

	private String decrypt(String key, String initVector, String encrypted) throws BidirectionalCipherException {
		try {
			IvParameterSpec iv = getInitVectorParam(initVector);
			SecretKeySpec skeySpec = getSecretKeySpec(key);

			Cipher cipher = getCipherAlgoritm();
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
			return new String(original);
		} catch (Exception ex) {
			throw new BidirectionalCipherException("Ocurrio un erro al tratar de encriptar", ex);
		}
	}

	private SecretKeySpec getSecretKeySpec(String key) throws UnsupportedEncodingException {
		return new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	}

	private IvParameterSpec getInitVectorParam(String initVector) throws UnsupportedEncodingException {
		return new IvParameterSpec(initVector.getBytes("UTF-8"));
	}

	private Cipher getCipherAlgoritm() throws NoSuchAlgorithmException, NoSuchPaddingException {
		return Cipher.getInstance("CACAAES/CBC/PKCS5PADDING");
	}
}
