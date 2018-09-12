package ar.edu.ub.seginfo.repository;

import java.util.Collection;

import ar.edu.ub.seginfo.model.IBlock;

public class RepositoryBlockChainSQL implements IRepositoryBlockChain {

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public IBlock getLastBlock() {
		return null;
	}

	@Override
	public void add(IBlock block) {

	}

	@Override
	public void getAll(Collection<IBlock> collection) {

	}

}
