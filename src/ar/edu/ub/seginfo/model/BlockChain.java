package ar.edu.ub.seginfo.model;

import ar.edu.ub.seginfo.cipher.Cipher;

public class BlockChain {
	private IRepository<Block>	repository;
	
	public BlockChain() {
		this.setRepository( new RepositoryRam() );
	}

	public IRepository<Block> getRepository() {
		return repository;
	}

	private void setRepository(IRepository<Block> repository) {
		this.repository = repository;
	}
	
	public void addBlock( String data ) {
		Block block = new Block( this.getRepository().getCount(), this.getRepository().getLastHash(), data, this.getCipher() );
		this.getRepository().add( block );
	}

	private Cipher getCipher() {
		return null;
	}
}
