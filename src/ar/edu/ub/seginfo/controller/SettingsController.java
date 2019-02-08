package ar.edu.ub.seginfo.controller;

import java.util.LinkedList;
import java.util.List;

import ar.edu.ub.seginfo.model.SettingsType;
import ar.edu.ub.seginfo.model.SettingsValue;
import ar.edu.ub.seginfo.util.Configuracion;
import ar.edu.ub.seginfo.view.SettingsView;

public class SettingsController {
	private SettingsView view;
	private Configuracion configuracion;
	private List<SettingsValue>	settings;
	
	public SettingsController(Configuracion configuracion) {
		this.setConfiguracion(configuracion);
		this.initSettings();
	}

	private void initSettings() {
		this.setSettings( new LinkedList<SettingsValue>());
		
		this.getSettings().add( new SettingsValue(" Path almacenamiento", "pathDatabase", SettingsType.TEXT_FIELD ) );
		this.getSettings().add( new SettingsValue(" URL TSP", "urlTSP", SettingsType.TEXT_FIELD ) );
		this.getSettings().add( new SettingsValue(" Usr TSP", "usrTSP", SettingsType.TEXT_FIELD ) );
		this.getSettings().add( new SettingsValue(" Pwd TSP", "pwdTSP", SettingsType.PASSWORD_FIELD ) );		
	}

	public void setVisible(boolean visible) {
		this.getView().setVisible(visible);		
	}

	private Configuracion getConfiguracion() {
		return configuracion;
	}

	private void setConfiguracion(Configuracion configuracion) {
		this.configuracion = configuracion;
	}

	private SettingsView getView() {
		return view;
	}

	public void setView(SettingsView view) {
		this.view = view;
	}

	public void showSettings() {
		for( SettingsValue v : this.getSettings() )
			this.getView().load( v, this.getConfiguracion() );
		
	}

	public List<SettingsValue> getSettings() {
		return settings;
	}

	public void setSettings(List<SettingsValue> settings) {
		this.settings = settings;
	}

	public void save() {
		for( SettingsValue v : this.getSettings() )
			this.getConfiguracion().setConfiguracion( v.getCfgEntry(), this.getView().getValueAsString( v ) );		
	}

}
