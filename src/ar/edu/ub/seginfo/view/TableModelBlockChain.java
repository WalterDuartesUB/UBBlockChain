package ar.edu.ub.seginfo.view;

import javax.swing.table.DefaultTableModel;

import ar.edu.ub.seginfo.model.IRepositoryBlockChain;

public class TableModelBlockChain extends DefaultTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TableModelBlockChain(IRepositoryBlockChain model) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getColumnCount() {
		return 2;
	}
	@Override
	public String getColumnName(int column) {
		return "columna";
	}	
}
