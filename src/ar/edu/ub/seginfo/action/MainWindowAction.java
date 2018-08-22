package ar.edu.ub.seginfo.action;

import ar.edu.ub.seginfo.cipher.CipherDummy;
import ar.edu.ub.seginfo.cipher.CipherDummyBidirectional;
import ar.edu.ub.seginfo.cipher.IBidirectionalCipher;
import ar.edu.ub.seginfo.cipher.ICipher;
import ar.edu.ub.seginfo.controller.MainWindowController;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.model.IRepositoryBlockChain;
import ar.edu.ub.seginfo.model.RepositoryBlockChainRam;
import ar.edu.ub.seginfo.view.MainWindowView;

public class MainWindowAction {

	public static void actionPerformed() {
		//Creo el modelo
		IRepositoryBlockChain 	repositoryBC = new RepositoryBlockChainRam();
		IBidirectionalCipher	bcDataCipher = new CipherDummyBidirectional();
		BlockChain 				bc = new BlockChain( repositoryBC, bcDataCipher );
		
		//Creo el metodo de generacion de hash de los archivos
		ICipher					hashGenerator = new CipherDummy();
		
		//Creo el controlador y la vista
		MainWindowController 	mwc = new MainWindowController( bc, hashGenerator );
		MainWindowView 			mwv = new MainWindowView();
		
		//Hago el enlazado de la vista y el controlador
		mwv.setController( mwc );
		mwc.setView( mwv );
		
		mwc.addModelListener( mwv );
		
		mwv.setModel( repositoryBC );
		
		//Muestro la ventana principal
		mwv.setVisible(true);
	}

}
