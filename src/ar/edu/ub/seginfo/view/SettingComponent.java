package ar.edu.ub.seginfo.view;

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.edu.ub.seginfo.model.SettingsValue;

public class SettingComponent {

	private JPanel panel;
	private JTextField	txtField;
	public SettingComponent(SettingsValue setting) {
		this.init(setting);
	}

	private void init(SettingsValue setting) {
		this.setPanel( new JPanel() );
		this.getPanel().setLayout( new GridLayout(1,2));
		
		JLabel label = new JLabel(setting.getLabel());
		this.getPanel().add( label);
		
		this.setTxtField( new JTextField() );
		this.getPanel().add( this.getTxtField() );
	}

	public void setText(String configuracion) {
		this.getTxtField().setText( configuracion );		
	}

	public void addTo(JDialog dialog) {
		dialog.add( this.getPanel() );		
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JTextField getTxtField() {
		return txtField;
	}

	public void setTxtField(JTextField txtField) {
		this.txtField = txtField;
	}

	public String getText() {
		return this.getTxtField().getText();
	}

}
