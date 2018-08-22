package ar.edu.ub.seginfo.model;

import java.util.Collection;

public interface IRepositoryBlockChain {
	IBlock	getLastBlock();
	void	add( IBlock block );
	void 	getAll(Collection<IBlock> collection);
}
