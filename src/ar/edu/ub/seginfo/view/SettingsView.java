package ar.edu.ub.seginfo.view;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;

import ar.edu.ub.seginfo.controller.SettingsController;
import ar.edu.ub.seginfo.model.SettingsValue;
import ar.edu.ub.seginfo.util.Configuracion;

public class SettingsView {
	private JDialog dialog;
	private SettingsController controller;
	
	private Map<String, SettingComponent> components;

	public SettingsView(MainWindowView mainWindowView) {
		this.init(mainWindowView);	
		this.setComponents( new HashMap<String, SettingComponent>());
	}

	private void init(MainWindowView mainWindowView) {
		this.setDialog( new JDialog( mainWindowView ) );
		this.getDialog().setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		this.getDialog().addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {				
				getController().save();
			}
		} );
		this.getDialog().setSize(300, 100);
		this.getDialog().setResizable(false);
		this.getDialog().setLayout( new GridLayout(0, 1));
		this.getDialog().setTitle("Preferencias");
		this.getDialog().setModal(true);
		
	}

	public void setVisible(boolean visible) {
		this.getDialog().setVisible(visible);		
	}

	private JDialog getDialog() {
		return dialog;
	}

	private void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}

	public SettingsController getController() {
		return controller;
	}

	public void setController(SettingsController controller) {
		this.controller = controller;
	}

	public void load(SettingsValue setting, Configuracion configuracion) {
		this.getSettingField( setting ).setText( configuracion.getConfiguracion( setting.getCfgEntry() ) );
	}

	private SettingComponent getSettingField(SettingsValue setting) {
		
		if( this.getComponents().get(setting.getCfgEntry()) == null ) 
			this.getComponents().put(setting.getCfgEntry(), createSettingComponent(setting));
			
		return this.getComponents().get(setting.getCfgEntry());
	}

	private SettingComponent createSettingComponent(SettingsValue setting) {
		SettingComponent c = new SettingComponent( setting );
		
		c.addTo( this.getDialog() );
		
		return c;
	}

	private Map<String, SettingComponent> getComponents() {
		return components;
	}

	private void setComponents(Map<String, SettingComponent> components) {
		this.components = components;
	}
	
	public String getValueAsString(SettingsValue setting ) {
		return this.getSettingField( setting ).getText();
	}

}
