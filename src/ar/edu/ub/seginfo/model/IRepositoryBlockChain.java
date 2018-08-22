package ar.edu.ub.seginfo.model;

public interface IRepositoryBlockChain {
	IBlock	getLastBlock();
	void	add( IBlock block );
}
