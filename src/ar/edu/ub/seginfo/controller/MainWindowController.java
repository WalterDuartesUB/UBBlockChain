package ar.edu.ub.seginfo.controller;

import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.model.RepositoryBlockChainRam;
import ar.edu.ub.seginfo.view.MainWindowView;

public class MainWindowController {
	private MainWindowView view;
	private BlockChain blockChain;
	
	public MainWindowController(){
		this.setBlockChain( new BlockChain( new RepositoryBlockChainRam() ) );
	}

	public MainWindowView getView() {
		return view;
	}

	public void setView(MainWindowView view) {
		this.view = view;
	}

	public void uploadFile( String filePath ) {
		
	}

	private BlockChain getBlockChain() {
		return blockChain;
	}

	private void setBlockChain(BlockChain blockChain) {
		this.blockChain = blockChain;
	}


}
