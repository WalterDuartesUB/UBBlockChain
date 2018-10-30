package ar.edu.ub.seginfo.repository;

import java.util.Collection;

import ar.edu.ub.seginfo.exception.RepositoryException;
import ar.edu.ub.seginfo.model.IBlock;

public interface IRepositoryBlockChain {
	boolean isEmpty()  throws RepositoryException;
	IBlock	getLastBlock() throws RepositoryException;
	void	add( IBlock block )  throws RepositoryException;
	void 	getAll(Collection<IBlock> collection)  throws RepositoryException;
}
