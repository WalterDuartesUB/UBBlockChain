package ar.edu.ub.seginfo.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.edu.ub.seginfo.cipher.hashgenerator.IHashGenerator;
import ar.edu.ub.seginfo.controller.FindFileController;
import ar.edu.ub.seginfo.model.IBlockFields;

public class FindFileView extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField txtCRC;
	
	private FindFileController controller;
	private IHashGenerator hashGenerator;
	
	public FindFileView(IHashGenerator hashGenerator)
	{
		this.init();
		this.initComponents();
		this.setHashGenerator(hashGenerator);
	}

	private void initComponents() {
		JPanel panel = new JPanel();
		
		panel.setLayout( new BorderLayout() );
		panel.add( this.createPanelFile(), BorderLayout.NORTH );
		panel.add( this.createPanelButton(), BorderLayout.SOUTH );
		
		this.add( panel, BorderLayout.NORTH );
	}

	private Component createPanelFile() {
		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout() );
		
		JLabel etiqueta = new JLabel("Hash");
		panel.add( etiqueta, BorderLayout.WEST );
		
		this.setTxtCRC( new JTextField() );
		panel.add( getTxtCRC(), BorderLayout.CENTER );
		
		JButton button = new JButton("...");		
		panel.add( button, BorderLayout.EAST );		
		
		return panel;
	}

	private Component createPanelButton() {
		JPanel panel = new JPanel();
		panel.setLayout( new BorderLayout() );
		
		JButton button = new JButton("Find the crc of the file in the repository");
		button.addActionListener( this::onClickFindButton );
		panel.add( button, BorderLayout.EAST );
		
		return panel;
	}

	private void init() {
		this.setSize(600, 80);
		this.setResizable(false);
		this.setLayout( new BorderLayout());
		this.setTitle("Find a file in the repository");
	}
	
	public void onClickFindButton(ActionEvent ae) {
		this.getController().findFile( this.getTxtCRC().getText() );
	}

	public JTextField getTxtCRC() {
		return txtCRC;
	}

	public void setTxtCRC(JTextField txtCRC) {
		this.txtCRC = txtCRC;
	}

	public FindFileController getController() {
		return controller;
	}

	public void setController(FindFileController controller) {
		this.controller = controller;
	}

	public IHashGenerator getHashGenerator() {
		return hashGenerator;
	}

	public void setHashGenerator(IHashGenerator hashGenerator) {
		this.hashGenerator = hashGenerator;
	}

	public void showError(String message) {
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.WARNING_MESSAGE);
	}

	public void showExistingBlock(IBlockFields block) {
		String message = "Existe el bloque y fue agregado en la fecha : " + new Timestamp(block.getTimeStamp()).toString();
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.INFORMATION_MESSAGE);
	}
}
