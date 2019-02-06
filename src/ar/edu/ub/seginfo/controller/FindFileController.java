package ar.edu.ub.seginfo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashGenerator;
import ar.edu.ub.seginfo.exception.BidirectionalCipherException;
import ar.edu.ub.seginfo.exception.BlockChainBlockException;
import ar.edu.ub.seginfo.exception.BlockInvalidFingerPrintException;
import ar.edu.ub.seginfo.exception.HashGeneratorException;
import ar.edu.ub.seginfo.exception.RepositoryException;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.model.IBlockFields;
import ar.edu.ub.seginfo.view.FindFileView;

public class FindFileController {
	private FindFileView view;
	private BlockChain blockChain;
	private IHashGenerator hashGenerator;
	public FindFileController(BlockChain blockChain, IHashGenerator hashGenerator) {
		this.setBlockChain(blockChain);
		this.setHashGenerator(hashGenerator);
	}

	public void findFile(String fileCRC) {
		try {
			IBlockFields block = this.getBlockChain().findBlockByCRC( fileCRC );			
			this.getView().showExistingBlock(block);			
		} catch (RepositoryException | BidirectionalCipherException | BlockChainBlockException | BlockInvalidFingerPrintException e) {
			this.getView().showError( e.getMessage() );
		}	
	}
	
	public FindFileView getView() {
		return view;
	}
	public void setView(FindFileView view) {
		this.view = view;
	}

	public BlockChain getBlockChain() {
		return blockChain;
	}

	public void setBlockChain(BlockChain blockChain) {
		this.blockChain = blockChain;
	}

	public void setFile(String filePath) {		
		try {
			this.getView().setHash( this.getHashGenerator().hash(  Files.readAllBytes(Paths.get(filePath)) ).getHashAsString() );
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HashGeneratorException e) {
			e.printStackTrace();
		}		
	}

	private IHashGenerator getHashGenerator() {
		return hashGenerator;
	}

	private void setHashGenerator(IHashGenerator hashGenerator) {
		this.hashGenerator = hashGenerator;
	}

}
