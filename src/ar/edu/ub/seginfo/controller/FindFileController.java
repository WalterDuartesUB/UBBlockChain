package ar.edu.ub.seginfo.controller;

import ar.edu.ub.seginfo.exception.BidirectionalCipherException;
import ar.edu.ub.seginfo.exception.BlockChainBlockException;
import ar.edu.ub.seginfo.exception.BlockInvalidFingerPrintException;
import ar.edu.ub.seginfo.exception.RepositoryException;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.model.IBlockFields;
import ar.edu.ub.seginfo.view.FindFileView;

public class FindFileController {
	private FindFileView view;
	private BlockChain blockChain;
	public FindFileController(BlockChain blockChain) {
		this.setBlockChain(blockChain);
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

}
