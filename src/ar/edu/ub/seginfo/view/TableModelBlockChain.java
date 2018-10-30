package ar.edu.ub.seginfo.view;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import ar.edu.ub.seginfo.model.IBlockFields;

public class TableModelBlockChain extends DefaultTableModel {
	private abstract class ModelColumnTable<T>{
		private String nombre;
		public ModelColumnTable(String nombre) {
			this.setNombre(nombre);
		}
		
		public String getNombre() {
			return nombre;
		}
		
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
		public abstract Object getValue(T data);
	}
	
	private class ModelColumnTablePreviousHash extends ModelColumnTable<IBlockFields>{

		public ModelColumnTablePreviousHash(String nombre) {
			super(nombre);
		}

		@Override
		public Object getValue(IBlockFields data) {
			return data.getPreviousHash();
		}
		
	}
	
	private class ModelColumnTableHash extends ModelColumnTable<IBlockFields>{
		
		public ModelColumnTableHash(String nombre) {
			super(nombre);
		}
		
		@Override
		public Object getValue(IBlockFields data) {
			return data.getHash();
		}
		
	}
	
	private class ModelColumnTableData extends ModelColumnTable<IBlockFields>{
		
		public ModelColumnTableData(String nombre) {
			super(nombre);
		}
		
		@Override
		public Object getValue(IBlockFields data) {
			return data.getData();
		}		
	}
	
	private class ModelColumnTableTimeStamp extends ModelColumnTable<IBlockFields>{
		
		public ModelColumnTableTimeStamp(String nombre) {
			super(nombre);
		}
		
		@Override
		public Object getValue(IBlockFields data) {
			return new Timestamp(data.getTimeStamp()).toString();
		}		
	}	
	
	///////////////////////////////////////////////////////////////////////////
	//
	
	private static final long serialVersionUID = 1L;
	private LinkedList<IBlockFields> blocks;
	private Map<Integer, ModelColumnTable<IBlockFields>> columnas;
	
	public TableModelBlockChain(LinkedList<IBlockFields> blocks) {
		this.setBlocks(blocks);
		
		this.setColumnas( new HashMap<Integer, ModelColumnTable<IBlockFields>>());
		this.getColumnas().put(0, new ModelColumnTablePreviousHash( "Prev. Hash") );
		this.getColumnas().put(1, new ModelColumnTableHash("Hash") );
		this.getColumnas().put(2, new ModelColumnTableData("Data") );
		this.getColumnas().put(3, new ModelColumnTableTimeStamp("TimeStamp") );
	}

	public void setBlocks(LinkedList<IBlockFields> blocks) {
		this.blocks = blocks;		
	}

	@Override
	public int getColumnCount() {
		return this.getColumnas().size();
	}
	@Override
	public String getColumnName(int column) {
		return getColumnas().get( column ).getNombre();
	}

	private Map<Integer, ModelColumnTable<IBlockFields>> getColumnas() {
		return columnas;
	}

	private void setColumnas(Map<Integer, ModelColumnTable<IBlockFields>> columnas) {
		this.columnas = columnas;
	}
	
	@Override
	public int getRowCount() {			
		return this.getBlocks() == null ? 0 : this.getBlocks().size();
	}
	
	private LinkedList<IBlockFields> getBlocks() {
		return this.blocks;		
	}

	@Override
	public Object getValueAt(int row, int column) {	
		return this.getColumnas().get( column ).getValue( this.getBlocks().get(row) );
	}	
}
