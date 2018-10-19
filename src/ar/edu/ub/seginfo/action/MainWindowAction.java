package ar.edu.ub.seginfo.action;

import java.util.HashMap;
import java.util.Map;

import ar.edu.ub.seginfo.cipher.bidirectionalcipher.CipherAES;
import ar.edu.ub.seginfo.cipher.bidirectionalcipher.IBidirectionalCipher;
import ar.edu.ub.seginfo.cipher.hashgenerator.HashGeneratorMD5;
import ar.edu.ub.seginfo.controller.MainWindowController;
import ar.edu.ub.seginfo.model.BlockChain;
import ar.edu.ub.seginfo.repository.IRepositoryBlockChain;
import ar.edu.ub.seginfo.repository.RepositoryBlockChainAccess;
import ar.edu.ub.seginfo.timestamping.ITimestampingProvider;
import ar.edu.ub.seginfo.timestamping.TimestampingProviderLocalFile;
import ar.edu.ub.seginfo.timestamping.TimestampingProviderSystem;
import ar.edu.ub.seginfo.timestamping.TimestampingProviderURL;
import ar.edu.ub.seginfo.util.Configuracion;
import ar.edu.ub.seginfo.view.MainWindowView;

public class MainWindowAction {

	public static void actionPerformed( Configuracion configuracion ) {
		//Creo el modelo
		BlockChain bc = createBlockChain(configuracion);
		
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
		return new MainWindowController( bc, new HashGeneratorMD5() );
	}

	private static BlockChain createBlockChain( Configuracion configuracion) {		
		IRepositoryBlockChain 	repositoryBC = new RepositoryBlockChainAccess( configuracion.getConfiguracion( "pathDatabase", "./database/database.accdb") );
		IBidirectionalCipher	bcDataCipher = new CipherAES();
		ITimestampingProvider	tsProvider = createTimestampingProvider( configuracion );
				
		return new BlockChain( repositoryBC, bcDataCipher, tsProvider );
	}

	private static ITimestampingProvider createTimestampingProvider(Configuracion configuracion) {
		Map<TipoTSP, ITimestampingProvider> providers = new HashMap<TipoTSP, ITimestampingProvider>();
		
		//Cargo los proveedores de tiempo disponibles
		providers.put( TipoTSP.SYSTEM_TIME, new TimestampingProviderSystem() );
		providers.put( TipoTSP.SYSTEM_TIME_IN_FILE, new TimestampingProviderLocalFile( configuracion.getConfiguracion( "pathTSA", "./TSAdata.txt") ) );
		providers.put( TipoTSP.TSA_SERVICE, new TimestampingProviderURL( configuracion.getConfiguracion( "urlTSP", "https://freetsa.org/tsr") ) );
		
		//Me quedo con el proveedor configurado
		return providers.get( TipoTSP.valueOf( configuracion.getConfiguracionAsInt("TipoTimestampingService", TipoTSP.SYSTEM_TIME.getValue() ) ) );
	}

}
