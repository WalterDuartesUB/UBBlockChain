package ar.edu.ub.seginfo.model;

import java.util.ArrayList;
import java.util.List;

public class RepositoryRam implements IRepository<Block> {
	private List<Block> blocks;
	
	public RepositoryRam()
	{
		this.setBlocks( new ArrayList<Block>() );
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

	@Override
	public int getCount() {
		return this.getBlocks().size();
	}

	@Override
	public String getLastHash() {
		if( this.getBlocks().size() == 0 )
			return "0000000";
			
		return this.getBlocks().get( this.getBlocks().size() - 1 ).getHash();
	}

}
