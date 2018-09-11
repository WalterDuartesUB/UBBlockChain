package ar.edu.ub.seginfo.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import ar.edu.ub.seginfo.cipher.bidirectionalcipher.IBidirectionalCipher;
import ar.edu.ub.seginfo.model.exception.BlockChainAlreadyExistsException;
import ar.edu.ub.seginfo.repository.IRepositoryBlockChain;
import ar.edu.ub.seginfo.timestamping.ITimestampingProvider;

public class BlockChain implements IBlockChain<IBlockFields>{
	private IRepositoryBlockChain repository;
	private IBidirectionalCipher dataCipher;
	private ITimestampingProvider tsProvider;
	
	public BlockChain(IRepositoryBlockChain repository, IBidirectionalCipher dataCipher, ITimestampingProvider tsProvider) {		
		this.setRepository(repository);
		this.setDataCipher(dataCipher);
		this.setTsProvider(tsProvider);
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
			return "00000000000000000000000000000000";
		
		return this.getRepository().getLastBlock().getHash();
	}

	public IBlock createBlock(String data) {
		return new Block( this.getLastHash(), data, this.getTsProvider().getTimeStamp( data ), this.getDataCipher() );		
	}		
	
	@Override
	public void getAll( Collection<IBlockFields> collection ) {			
		Collection<IBlock> blocks = new LinkedList<IBlock>();
		
		this.getRepository().getAll( blocks );
		
		for( IBlock b : blocks )
			collection.add( new Block( b, this.getDataCipher() ) );
	}

	private ITimestampingProvider getTsProvider() {
		return tsProvider;
	}

	private void setTsProvider(ITimestampingProvider tsProvider) {
		this.tsProvider = tsProvider;
	}
}
