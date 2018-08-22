package ar.edu.ub.seginfo.model;

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
}
