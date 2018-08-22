package ar.edu.ub.seginfo.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ar.edu.ub.seginfo.cipher.IBidirectionalCipher;

public class BlockChain {
	private IRepositoryBlockChain repository;
	private IBidirectionalCipher dataCipher;
	
	public BlockChain(IRepositoryBlockChain repository, IBidirectionalCipher dataCipher) {		
		this.setRepository(repository);
		this.setDataCipher(dataCipher);
	}

	private IRepositoryBlockChain getRepository() {
		return repository;
	}

	private void setRepository(IRepositoryBlockChain repository) {
		this.repository = repository;
	}
	
	public void addBlock( IBlock block ) throws BlockChainAlreadyExistsException{
		IBlock existingBlock = this.findBlock(block);
		
		if( existingBlock != null )
			throw new BlockChainAlreadyExistsException("El documento elegido ya existe en la blockchain. ");
		
		this.getRepository().add(block);
	}
	
	private IBlock findBlock(IBlock block) {
		Collection<IBlock> blocks = new LinkedList<IBlock>();
		
		this.getRepository().getAll( blocks );
		
		for( IBlock b : blocks )
			if( this.haveTheSameData(block, b) )
				return b;
		
		return null;
	}

	private boolean haveTheSameData(IBlock block, IBlock b) {
		return b.hasTheSameDataThan( block );
	}

	public boolean isValidChain() {
		List< IBlock > blocks = new LinkedList<IBlock>();
		
		this.getRepository().getAll( blocks );
		
		for( IBlock b : blocks )
			if( !b.isValidBlock() )
				return false;
		return true;
	}

	private IBidirectionalCipher getDataCipher() {
		return dataCipher;
	}

	private void setDataCipher(IBidirectionalCipher dataCipher) {
		this.dataCipher = dataCipher;
	}
	
	public String getLastHash() {		
		//TODO pendiente que devolver
		if( this.getRepository().isEmpty() )
			return "000000000000000000";
		
		return this.getRepository().getLastBlock().getHash();
	}

	public IBlock createBlock(String data) {
		return new Block( this.getLastHash(), data, this.getTimeStamp(), this.getDataCipher() );		
	}

	private long getTimeStamp() {
		return System.currentTimeMillis();
	}	
}
