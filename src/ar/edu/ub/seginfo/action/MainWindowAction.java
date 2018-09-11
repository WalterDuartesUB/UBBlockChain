package ar.edu.ub.seginfo.action;

import ar.edu.ub.seginfo.cipher.bidirectionalcipher.CipherDummyBidirectional;
import ar.edu.ub.seginfo.cipher.bidirectionalcipher.IBidirectionalCipher;
import ar.edu.ub.seginfo.cipher.hashgenerator.HashGeneratorDummy;
import ar.edu.ub.seginfo.cipher.hashgenerator.IHashGenerator;
import ar.edu.ub.seginfo.controller.MainWindowController;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.repository.IRepositoryBlockChain;
import ar.edu.ub.seginfo.repository.RepositoryBlockChainRam;
import ar.edu.ub.seginfo.timestamping.ITimestampingProvider;
import ar.edu.ub.seginfo.timestamping.TimestampingProviderURL;
import ar.edu.ub.seginfo.view.MainWindowView;

public class MainWindowAction {

	public static void actionPerformed() {
		//Creo el modelo
		IRepositoryBlockChain 	repositoryBC = new RepositoryBlockChainRam();
		IBidirectionalCipher	bcDataCipher = new CipherDummyBidirectional();
		ITimestampingProvider	tsProvider = new TimestampingProviderURL();
		
		BlockChain 				bc = new BlockChain( repositoryBC, bcDataCipher, tsProvider );
		
		//Creo el metodo de generacion de hash de los archivos
		IHashGenerator					hashGenerator = new HashGeneratorDummy();
				
		//Creo el controlador y la vista
		MainWindowController 	mwc = new MainWindowController( bc, hashGenerator );
		MainWindowView 			mwv = new MainWindowView();
		
		//Hago el enlazado de la vista y el controlador
		mwv.setController( mwc );
		mwc.setView( mwv );
		
		mwc.addModelListener( mwv );
		
		mwv.setModel( bc );
		
		//Muestro la ventana principal
		mwv.setVisible(true);
	}

}
