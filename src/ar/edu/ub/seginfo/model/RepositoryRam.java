package ar.edu.ub.seginfo.model;

import java.util.LinkedList;
import java.util.List;

public class RepositoryRam implements IRepository<Block> {
	private List<Block> blocks;
	
	public RepositoryRam()
	{
		this.setBlocks( new LinkedList<Block>() );
	}
	
	@Override
	public boolean add(Block dato) {
		
		this.getBlocks().add( dato );
		
		return false;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	private void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

}
