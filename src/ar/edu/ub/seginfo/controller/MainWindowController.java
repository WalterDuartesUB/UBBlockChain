package ar.edu.ub.seginfo.controller;

import ar.edu.ub.seginfo.cipher.IBidirectionalCipher;
import ar.edu.ub.seginfo.cipher.ICipher;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.model.RepositoryBlockChainRam;
import ar.edu.ub.seginfo.view.MainWindowView;

public class MainWindowController {
	private MainWindowView 			view;
	private BlockChain 				blockChain;
	private IBidirectionalCipher 	blockChainDataCipher;
	private ICipher					hashGenerator;
	
	public MainWindowController(){
		this( null, null );
	}

	public MainWindowController( ICipher hashGenerator, IBidirectionalCipher blockChainDataCipher ){
		this.setBlockChainDataCipher( blockChainDataCipher );
		this.setHashGenerator( hashGenerator );
		
		this.setBlockChain( new BlockChain( new RepositoryBlockChainRam(), this.getBlockChainDataCipher() ) );
	}
	
	public MainWindowView getView() {
		return view;
	}

	public void setView(MainWindowView view) {
		this.view = view;
	}

	public void uploadFile( String filePath ) {
		//Leo el archivo de alguna forma
		
		//Genero el hash de archivo
		
		//Lo agrego a la blockchain
		
		//Si algo salio mal, muestro el mensaje
	}

	private BlockChain getBlockChain() {
		return blockChain;
	}

	private void setBlockChain(BlockChain blockChain) {
		this.blockChain = blockChain;
	}

	private IBidirectionalCipher getBlockChainDataCipher() {
		return blockChainDataCipher;
	}

	private void setBlockChainDataCipher(IBidirectionalCipher dataCipher) {
		this.blockChainDataCipher = dataCipher;
	}

	private ICipher getHashGenerator() {
		return hashGenerator;
	}

	private void setHashGenerator(ICipher hashGenerator) {
		this.hashGenerator = hashGenerator;
	}


}
