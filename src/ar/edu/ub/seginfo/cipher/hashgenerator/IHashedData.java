package ar.edu.ub.seginfo.cipher.hashgenerator;

public interface IHashedData {
	public String getHashAsString();

	public byte[] getHash();

	public String getDigestAlgorithm();
}
