package ar.edu.ub.seginfo.model;

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
}
