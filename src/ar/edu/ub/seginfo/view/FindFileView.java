package ar.edu.ub.seginfo.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.edu.ub.seginfo.controller.FindFileController;
import ar.edu.ub.seginfo.model.IBlockFields;

public class FindFileView {
	private JTextField txtCRC;
	
	private FindFileController controller;	
	private JDialog dialog;
	
	public FindFileView(MainWindowView mainWindowView)
	{
		this.init(mainWindowView);
		this.initComponents();
	}

	private void initComponents() {
		JPanel panel = new JPanel();
		
		panel.setLayout( new BorderLayout() );
		panel.add( this.createPanelFile(), BorderLayout.NORTH );
		panel.add( this.createPanelButton(), BorderLayout.SOUTH );
		
		this.getDialog().add( panel, BorderLayout.NORTH );
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
		button.addActionListener( this::onClickExploreButton );
		
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

	private void init(MainWindowView mainWindowView) {
		this.setDialog( new JDialog( mainWindowView ) );
		this.getDialog().setSize(600, 80);
		this.getDialog().setResizable(false);
		this.getDialog().setLayout( new BorderLayout());
		this.getDialog().setTitle("Find a file in the repository");
		this.getDialog().setModal(true);		
	}
	
	public void onClickExploreButton(ActionEvent ae) {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		int result = fileChooser.showOpenDialog(this.getDialog());

		if (result == JFileChooser.APPROVE_OPTION)
			this.getController().setFile( fileChooser.getSelectedFile().getAbsolutePath() );		
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

	public void showError(String message) {
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.WARNING_MESSAGE);
	}

	public void showExistingBlock(IBlockFields block) {
		String message = "Existe el bloque y fue agregado en la fecha : " + new Timestamp(block.getTimeStamp()).toString();
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.INFORMATION_MESSAGE);
	}

	public void setHash(String hashFile) {
		this.getTxtCRC().setText( hashFile );
		
	}

	private JDialog getDialog() {
		return dialog;
	}

	private void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}

	public void setVisible(boolean visible) {
		this.getDialog().setVisible( visible );		
	}
}
