package ar.edu.ub.seginfo.model;

public class SettingsValue {

	private String label;
	private String cfgEntry;
	private SettingsType componentType;
	public SettingsValue(String label, String cfgEntry, SettingsType componentType) {
		this.setCfgEntry(cfgEntry);
		this.setComponentType(componentType);
		this.setLabel(label);
	}
	public String getLabel() {
		return label;
	}
	private void setLabel(String label) {
		this.label = label;
	}
	public String getCfgEntry() {
		return cfgEntry;
	}
	private void setCfgEntry(String cfgEntry) {
		this.cfgEntry = cfgEntry;
	}
	public SettingsType getComponentType() {
		return componentType;
	}
	private void setComponentType(SettingsType componentType) {
		this.componentType = componentType;
	}

}
