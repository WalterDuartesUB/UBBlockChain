package ar.edu.ub.seginfo.model;

import java.util.LinkedList;
import java.util.List;

public class BlockChain {
	private IRepositoryBlockChain repository;

	public BlockChain(IRepositoryBlockChain repository) {		
		this.setRepository(repository);
	}

	private IRepositoryBlockChain getRepository() {
		return repository;
	}

	private void setRepository(IRepositoryBlockChain repository) {
		this.repository = repository;
	}
	
	public void addBlock( IBlock block ) {
		this.getRepository().add(block);
	}
	
	public boolean isValidChain() {
		List< IBlock > blocks = new LinkedList<IBlock>();
		
		this.getRepository().getAll( blocks );
		
		for( IBlock b : blocks )
			if( !b.isValidBlock() )
				return false;
		return true;
	}
}
