package ar.edu.ub.seginfo.action;

import ar.edu.ub.seginfo.controller.MainWindowController;
import ar.edu.ub.seginfo.view.MainWindowView;

public class MainWindowAction {

	public static void actionPerformed() {
		MainWindowController mwc = new MainWindowController();
		MainWindowView mwv = new MainWindowView();
		
		//Hago el enlazado de la vista y el controlador
		mwv.setController( mwc );
		mwc.setView( mwv );
		
		mwc.addModelListener( mwv );
		
		mwv.setModel(null);
		
		//Muestro la ventana principal
		mwv.setVisible(true);
	}

}
