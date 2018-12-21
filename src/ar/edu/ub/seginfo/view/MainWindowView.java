package ar.edu.ub.seginfo.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import ar.edu.ub.seginfo.controller.IModelListener;
import ar.edu.ub.seginfo.controller.MainWindowController;
import ar.edu.ub.seginfo.model.IBlockChain;
import ar.edu.ub.seginfo.model.IBlockFields;

public class MainWindowView extends JFrame implements IModelListener {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private MainWindowController controller;
	private IBlockChain<IBlockFields> blockChain;
	private TableModelBlockChain tableModel;

	private JTable tablaBlockChain;

	public MainWindowView() {
		this.init();
		this.createMenu();
		this.createButtons();

	}

	private void createMenu() {
		JMenuBar	menu = new JMenuBar();
				
		menu.add( this.createMenuFile() );
		menu.add( this.createMenuHelp() );
		
		this.setJMenuBar( menu );
	}

	private JMenu createMenuFile() {
		JMenu	menu = new JMenu("File");
		
		menu.add( this.createMenuItemUploadFile() );
		menu.add( this.createMenuItemCheckFile() );
		menu.addSeparator();
		
		
		return menu;
	}
	
	private JMenu createMenuHelp() {
		JMenu	menu = new JMenu("Settings");
		
		menu.add( this.createMenuItemSettings() );		
				
		return menu;
	}

	private JMenuItem createMenuItemSettings() {
		JMenuItem	menuitem = new JMenuItem("Settings");
		
		menuitem.addActionListener( this::onBtnClickSettings );
		
		return menuitem;
	}

	private JMenuItem createMenuItemCheckFile() {
		JMenuItem	menuitem = new JMenuItem("Find File in the repository");
		
		menuitem.addActionListener( this::onBtnClickFindFile );
		
		return menuitem;
	}

	private JMenuItem createMenuItemUploadFile() {
		JMenuItem	menuitem = new JMenuItem("Upload File");
		
		menuitem.addActionListener( this::onBtnClickUploadFile );
		
		return menuitem;
	}

	private void createButtons() {
		JButton btnUploadFile = new JButton("Upload file");

		btnUploadFile.addActionListener(this::onBtnClickUploadFile);

		this.add(btnUploadFile, BorderLayout.SOUTH);

		// Creo la tabla donde voy a visualizar la blockchain actual
		this.setTablaBlockChain(new JTable());

		this.setTableModel(new TableModelBlockChain(new LinkedList<IBlockFields>()));
		this.getTablaBlockChain().setModel(this.getTableModel());

		this.add(this.getTablaBlockChain().getTableHeader(), BorderLayout.PAGE_START);
		this.add(this.getTablaBlockChain(), BorderLayout.CENTER);
	}

	public void setModel(IBlockChain<IBlockFields> blockChain) {
		this.blockChain = blockChain;

		this.updateTableModel();
	}

	private void updateTableModel() {
		// Pido todos los bloques al repositorio
		LinkedList<IBlockFields> blocks = new LinkedList<IBlockFields>();
		try {
			this.getBlockChain().getAll(blocks);
			this.getTableModel().setBlocks(blocks);
		} catch (Exception e) {
			this.showError(e.getMessage());
		}
	}

	public void onBtnClickFindFile(ActionEvent ae) {
		this.getController().findFile();
	}

	private void showUnimplementedMethodMessage() {
		this.showError("Unimplemented method");
	}

	public void onBtnClickSettings(ActionEvent ae) {
		showUnimplementedMethodMessage();
	}
	
	public void onBtnClickUploadFile(ActionEvent ae) {
		try {
			this.getController().uploadFile(this.getFilePath());
		} catch (Exception e) {
			this.showError(e.getMessage());
		}
	}

	private String getFilePath() throws RuntimeException {
		// Presento un dialogo para que el usuario elija el archivo a subir a la block
		// chain
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

		int result = fileChooser.showOpenDialog(this);

		if (result != JFileChooser.APPROVE_OPTION)
			throw new RuntimeException("Operación cancelada por el usuario");

		return fileChooser.getSelectedFile().getAbsolutePath();
	}

	private void init() {
		this.setSize(600, 600);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public MainWindowController getController() {
		return controller;
	}

	public void setController(MainWindowController controller) {
		this.controller = controller;
	}

	private JTable getTablaBlockChain() {
		return tablaBlockChain;
	}

	private void setTablaBlockChain(JTable tablaBlockChain) {
		this.tablaBlockChain = tablaBlockChain;
	}

	@Override
	public void update() {
		this.updateTableModel();

		// Mando la orden para redibujar
		this.revalidate();
		this.repaint();
	}

	private IBlockChain<IBlockFields> getBlockChain() {
		return this.blockChain;
	}

	public void showError(String message) {
		JOptionPane.showMessageDialog(null, message, null, JOptionPane.WARNING_MESSAGE);
	}

	private TableModelBlockChain getTableModel() {
		return tableModel;
	}

	private void setTableModel(TableModelBlockChain tableModel) {
		this.tableModel = tableModel;
	}

}
