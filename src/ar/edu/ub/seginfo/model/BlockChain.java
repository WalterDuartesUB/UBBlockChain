package ar.edu.ub.seginfo.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import ar.edu.ub.seginfo.cipher.bidirectionalcipher.IBidirectionalCipher;
import ar.edu.ub.seginfo.cipher.hashgenerator.IHashedData;
import ar.edu.ub.seginfo.exception.BidirectionalCipherException;
import ar.edu.ub.seginfo.exception.BlockAlreadyExistsException;
import ar.edu.ub.seginfo.exception.BlockChainBlockException;
import ar.edu.ub.seginfo.exception.BlockInvalidFingerPrintException;
import ar.edu.ub.seginfo.exception.RepositoryException;
import ar.edu.ub.seginfo.exception.TimestampingException;
import ar.edu.ub.seginfo.repository.IRepositoryBlockChain;
import ar.edu.ub.seginfo.timestamping.ITimestampingProvider;

public class BlockChain implements IBlockChain<IBlockFields> {
	private static final String INITIAL_PREVIOUS_HASH = "00000000000000000000000000000000";
	private IRepositoryBlockChain repository;
	private IBidirectionalCipher dataCipher;
	private ITimestampingProvider tsProvider;

	public BlockChain(IRepositoryBlockChain repository, IBidirectionalCipher dataCipher,
			ITimestampingProvider tsProvider) {
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

	public void addBlock(IHashedData hashedData) throws BlockAlreadyExistsException, RepositoryException,
			TimestampingException, BidirectionalCipherException, BlockChainBlockException,
			BlockInvalidFingerPrintException {
		addBlock(createBlock(hashedData));
	}

	private void addBlock(IBlock block) throws BlockAlreadyExistsException, RepositoryException,
			BidirectionalCipherException, BlockChainBlockException, BlockInvalidFingerPrintException {
		IBlockFields existingBlock = this.findBlock(block);

		if (existingBlock != null)
			throw new BlockAlreadyExistsException("El documento elegido ya existe en la blockchain", existingBlock);

		this.getRepository().add(block);
	}

	private IBlockFields findBlock(IBlock block) throws RepositoryException, BidirectionalCipherException,
			BlockChainBlockException, BlockInvalidFingerPrintException {
		Collection<IBlockFields> blocks = new LinkedList<IBlockFields>();

		this.getAll(blocks);

		for (IBlockFields existingBlock : blocks)
			if (this.haveTheSameData(block, existingBlock))
				return existingBlock;

		return null;
	}

	private boolean haveTheSameData(IBlock block, IBlock b) throws BidirectionalCipherException {
		return b.hasTheSameDataThan(block);
	}

	private IBidirectionalCipher getDataCipher() {
		return dataCipher;
	}

	private void setDataCipher(IBidirectionalCipher dataCipher) {
		this.dataCipher = dataCipher;
	}

	public String getLastHash() throws RepositoryException {
		// Si esta vacia, pongo el hash de inicio
		if (this.getRepository().isEmpty())
			return INITIAL_PREVIOUS_HASH;

		return this.getRepository().getLastBlock().getHash();
	}

	private IBlock createBlock(IHashedData data)
			throws RepositoryException, TimestampingException, BidirectionalCipherException {
		return new Block(this.getLastHash(), this.getTsProvider().stamp(data), this.getDataCipher());
	}

	@Override
	public void getAll(Collection<IBlockFields> collection) throws RepositoryException, BidirectionalCipherException,
			BlockChainBlockException, BlockInvalidFingerPrintException {
		Collection<IBlock> blocks = new LinkedList<IBlock>();

		this.getRepository().getAll(blocks);

		Set<String> hashes = new TreeSet<String>();
		Set<String> fingerprints = new TreeSet<String>();

		// Siempre tengo presente el hash inicial
		hashes.add(INITIAL_PREVIOUS_HASH);

		for (IBlock b : blocks) {
			IBlockFields block = Block.createBlock(b, this.getDataCipher());

			if (!hashes.contains(block.getPreviousHash()))
				throw new BlockChainBlockException(
						"La blockchain es invalida porque un bloque apunta a otro que no existe.");

			if (!hashes.add(block.getHash()))
				throw new BlockChainBlockException("La blockchain es invalida porque un bloque esta repetido.");

			if (!fingerprints.add(block.getData()))
				throw new BlockChainBlockException("La blockchain es invalida porque un fingerprint esta repetido.");

			// Agrego a la coleccion el bloque que acabo de cargar
			collection.add(block);
		}
	}

	private ITimestampingProvider getTsProvider() {
		return tsProvider;
	}

	private void setTsProvider(ITimestampingProvider tsProvider) {
		this.tsProvider = tsProvider;
	}

	public IBlockFields findBlockByCRC(String fileCRC) throws RepositoryException, BidirectionalCipherException, BlockChainBlockException, BlockInvalidFingerPrintException {
		Collection<IBlockFields> blocks = new LinkedList<IBlockFields>();
		
		this.getAll(blocks);
		
		for( IBlockFields block : blocks )
			if( block.getData().compareTo( fileCRC ) == 0 )
				return block;
		
		throw new BlockChainBlockException("No existe un bloque con el hash " + fileCRC);
	}
}
