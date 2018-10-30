package ar.edu.ub.seginfo.model;

import ar.edu.ub.seginfo.exception.BidirectionalCipherException;

public interface IBlock {
	String getPreviousHash();

	String getHash();

	boolean hasTheSameDataThan(IBlock block) throws BidirectionalCipherException;
}
