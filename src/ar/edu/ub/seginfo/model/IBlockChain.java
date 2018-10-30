package ar.edu.ub.seginfo.model;

import java.util.Collection;

import ar.edu.ub.seginfo.cipher.bidirectionalcipher.BidirectionalCipherException;
import ar.edu.ub.seginfo.exception.RepositoryException;

public interface IBlockChain<T> {
	void getAll( Collection<T> collection) throws RepositoryException, BidirectionalCipherException;
}
