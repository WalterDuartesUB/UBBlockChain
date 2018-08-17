package ar.edu.ub.seginfo.model;

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
}
