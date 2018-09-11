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
		BlockChain bc = createBlockChain();
		
		MainWindowController 	mwc = createController(bc);
		MainWindowView 			mwv = new MainWindowView();
		
		//Hago el enlazado de la vista y el controlador
		mwv.setController( mwc );
		mwc.setView( mwv );
		
		mwc.addModelListener( mwv );
		
		//Ato el modelo a la vista
		mwv.setModel( bc );
		
		//Muestro la ventana principal
		mwv.setVisible(true);
	}

	private static MainWindowController createController(BlockChain bc) {
		//Creo el metodo de generacion de hash de los archivos
		IHashGenerator hashGenerator = new HashGeneratorDummy();
		
		return new MainWindowController( bc, hashGenerator );
	}

	private static BlockChain createBlockChain() {
		IRepositoryBlockChain 	repositoryBC = new RepositoryBlockChainRam();
		IBidirectionalCipher	bcDataCipher = new CipherDummyBidirectional();
		ITimestampingProvider	tsProvider = new TimestampingProviderURL( "https://freetsa.org/tsr" );
				
		return new BlockChain( repositoryBC, bcDataCipher, tsProvider );
	}

}
