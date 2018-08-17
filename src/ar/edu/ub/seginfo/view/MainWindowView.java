package ar.edu.ub.seginfo.view;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import ar.edu.ub.seginfo.controller.MainWindowController;

public class MainWindowView extends JFrame{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private MainWindowController controller;
	
	public MainWindowView(){
		this.init();
		this.createButtons();
		
	}

	private void createButtons() {
		JButton btnUploadFile = new JButton("Upload file");
		btnUploadFile.addActionListener( this::onBtnClickUploadFile );
		this.add(btnUploadFile);
	}
	
	public void onBtnClickUploadFile(ActionEvent ae) {
		this.getController().uploadFile( this.getFilePath() );
	}

	private String getFilePath() {
		// Presento un dialogo para que el usuario elija el archivo a subir a la block chain
		return null;
	}

	private void init() {
		this.setSize(600,600);
		this.setResizable( false );
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public MainWindowController getController() {
		return controller;
	}

	public void setController(MainWindowController controller) {
		this.controller = controller;
	}

}
