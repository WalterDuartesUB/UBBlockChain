package ar.edu.ub.seginfo.repository;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ar.edu.ub.seginfo.model.IBlock;

public class RepositoryBlockChainRam implements IRepositoryBlockChain {
	
	private List< IBlock > blocks;

	public RepositoryBlockChainRam() {
		this.setBlocks( new LinkedList<IBlock>() );
	}
	
	@Override
	public IBlock getLastBlock() {
		return this.getBlocks().get( this.getBlocks().size() - 1);
	}

	@Override
	public void add(IBlock block) {
		this.getBlocks().add( block );
	}

	@Override
	public void getAll(Collection<IBlock> collection) {
		collection.addAll( this.getBlocks() );
	}

	private List< IBlock > getBlocks() {
		return blocks;
	}

	private void setBlocks(List< IBlock > blocks) {
		this.blocks = blocks;
	}

	@Override
	public boolean isEmpty() {
		return this.getBlocks().isEmpty();
	}

}
