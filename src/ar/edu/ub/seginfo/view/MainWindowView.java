package ar.edu.ub.seginfo.view;

import javax.swing.JFrame;

import ar.edu.ub.seginfo.controller.MainWindowController;

public class MainWindowView extends JFrame{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private MainWindowController controller;
	
	public MainWindowView(){
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
