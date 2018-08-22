package ar.edu.ub.seginfo.controller;

import java.util.LinkedList;
import java.util.List;

import ar.edu.ub.seginfo.cipher.ICipher;
import ar.edu.ub.seginfo.model.Block;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.view.MainWindowView;

public class MainWindowController {
	private MainWindowView 			view;
	private BlockChain 				blockChain;	
	private ICipher					hashGenerator;
	private List<IModelListener>	modelListeners;
	
	public MainWindowController( BlockChain blockChain ){
		this( blockChain, null );
	}
	
	public MainWindowController( BlockChain blockChain, ICipher hashGenerator ) {
		this.setBlockChain(blockChain);
		this.setHashGenerator(hashGenerator);
		
		this.setModelListeners( new LinkedList<IModelListener>() );
	}

	public MainWindowView getView() {
		return view;
	}

	public void setView(MainWindowView view) {
		this.view = view;
	}

	public void addModelListener( IModelListener ml ) {
		this.getModelListeners().add( ml );
	}
	
	public void uploadFile( String filePath ) {
		String fileFingerPrint = getFileFingerPrint( filePath );
		
		//Lo agrego a la blockchain
		this.getBlockChain().addBlock( new Block( this.getBlockChain().getLastHash(), fileFingerPrint, System.currentTimeMillis() ));
		
		this.dispatchModelUpdate();
		
		//Si algo salio mal, muestro el mensaje	
	}

	private String getFileFingerPrint( String filePath ) {
		return "finger print del archivo";
	}

	private void dispatchModelUpdate() {
		for( IModelListener ml : this.getModelListeners() )
			ml.update();		
	}

	private BlockChain getBlockChain() {
		return blockChain;
	}

	private void setBlockChain(BlockChain blockChain) {
		this.blockChain = blockChain;
	}

	private ICipher getHashGenerator() {
		return hashGenerator;
	}

	private void setHashGenerator(ICipher hashGenerator) {
		this.hashGenerator = hashGenerator;
	}

	private List<IModelListener> getModelListeners() {
		return modelListeners;
	}

	private void setModelListeners(List<IModelListener> modelListeners) {
		this.modelListeners = modelListeners;
	}


}
