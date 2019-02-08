package ar.edu.ub.seginfo.action;

import ar.edu.ub.seginfo.controller.SettingsController;
import ar.edu.ub.seginfo.util.Configuracion;
import ar.edu.ub.seginfo.view.MainWindowView;
import ar.edu.ub.seginfo.view.SettingsView;

public class SettingsAction {
	public static void actionPerformed( Configuracion configuracion, MainWindowView mainWindowView) {
		SettingsView view = new SettingsView( mainWindowView );
		SettingsController controller = new SettingsController(configuracion); 
		
		controller.setView(view);
		view.setController(controller);
		
		controller.showSettings();
		controller.setVisible(true); 
		
	}
}
