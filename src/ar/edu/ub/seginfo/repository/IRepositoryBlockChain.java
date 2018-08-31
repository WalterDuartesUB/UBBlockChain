package ar.edu.ub.seginfo.repository;

import java.util.Collection;

import ar.edu.ub.seginfo.model.IBlock;

public interface IRepositoryBlockChain {
	boolean isEmpty();
	IBlock	getLastBlock();
	void	add( IBlock block );
	void 	getAll(Collection<IBlock> collection);
}
