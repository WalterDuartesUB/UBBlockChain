package ar.edu.ub.seginfo.controller;

import java.util.LinkedList;
import java.util.List;

import ar.edu.ub.seginfo.cipher.CipherDummy;
import ar.edu.ub.seginfo.cipher.ICipher;
import ar.edu.ub.seginfo.model.Block;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.model.IBlock;
import ar.edu.ub.seginfo.view.MainWindowView;

public class MainWindowController {
	private MainWindowView 			view;
	private BlockChain 				blockChain;	
	private ICipher					hashGenerator;
	private List<IModelListener>	modelListeners;
	
	public MainWindowController( BlockChain blockChain ){
		this( blockChain, new CipherDummy() );
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
		
		try {
			
			//Lo agrego a la blockchain
			this.getBlockChain().addBlock( this.createBlock( filePath ) );
				
			//informo que se agrego un block en la blockchain a los observadores
			this.dispatchModelUpdate();
			
		} catch (Exception e) {
			//Si algo salio mal, muestro el mensaje
			this.getView().showError( e.getMessage() );
		}	
	}

	private IBlock createBlock(String filePath) {
		return this.getBlockChain().createBlock( this.getFileFingerPrint(filePath) );
	}

	private String getFileFingerPrint( String filePath ) {
		return this.getHashGenerator().generateHash( "finger print del archivo" );
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
