package ar.edu.ub.seginfo.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import ar.edu.ub.seginfo.model.IBlock;
import ar.edu.ub.seginfo.model.IRepositoryBlockChain;

public class TableModelBlockChain extends DefaultTableModel {
	//TODO Esto deberia estar en otro paquete para poder ser reutilizado
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
	
	//TODO Esto deberia estar en otro paquete 
	private class ModelColumnTablePreviousHash extends ModelColumnTable<IBlock>{

		public ModelColumnTablePreviousHash(String nombre) {
			super(nombre);
		}

		@Override
		public Object getValue(IBlock data) {
			return data.getPreviousHash();
		}
		
	}

	//TODO Esto deberia estar en otro paquete
	private class ModelColumnTableHash extends ModelColumnTable<IBlock>{
		
		public ModelColumnTableHash(String nombre) {
			super(nombre);
		}
		
		@Override
		public Object getValue(IBlock data) {
			return data.getHash();
		}
		
	}
	
	private class ModelColumnTableData extends ModelColumnTable<IBlock>{
		
		public ModelColumnTableData(String nombre) {
			super(nombre);
		}
		
		@Override
		public Object getValue(IBlock data) {
			return "Data que deberia estar desencriptada";
		}		
	}
	
	private class ModelColumnTableTimeStamp extends ModelColumnTable<IBlock>{
		
		public ModelColumnTableTimeStamp(String nombre) {
			super(nombre);
		}
		
		@Override
		public Object getValue(IBlock data) {
			return "Time stamp que sale del data desencriptado";
		}		
	}	
	
	///////////////////////////////////////////////////////////////////////////
	//
	
	private static final long serialVersionUID = 1L;
	private IRepositoryBlockChain model;
	private Map<Integer, ModelColumnTable<IBlock>> columnas;
	
	public TableModelBlockChain(IRepositoryBlockChain model) {
		this.setModel(model);
		
		this.setColumnas( new HashMap<Integer, ModelColumnTable<IBlock>>());
		this.getColumnas().put(0, new ModelColumnTablePreviousHash( "Prev. Hash") );
		this.getColumnas().put(1, new ModelColumnTableHash("Hash") );
		this.getColumnas().put(2, new ModelColumnTableData("Data") );
		this.getColumnas().put(3, new ModelColumnTableTimeStamp("TimeStamp") );
	}

	@Override
	public int getColumnCount() {
		return this.getColumnas().size();
	}
	@Override
	public String getColumnName(int column) {
		return getColumnas().get( column ).getNombre();
	}

	private IRepositoryBlockChain getModel() {
		return model;
	}

	private void setModel(IRepositoryBlockChain model) {
		this.model = model;
	}

	private Map<Integer, ModelColumnTable<IBlock>> getColumnas() {
		return columnas;
	}

	private void setColumnas(Map<Integer, ModelColumnTable<IBlock>> columnas) {
		this.columnas = columnas;
	}
	
	@Override
	public int getRowCount() {			
		return this.getBlocks() == null ? 0 : this.getBlocks().size();
	}
	
	private LinkedList<IBlock> getBlocks() {
		
		if( this.getModel() == null )
			return null;
		
		LinkedList<IBlock> blocks = new LinkedList<IBlock>();
		
		this.getModel().getAll(blocks);
		
		return blocks;
	}

	@Override
	public Object getValueAt(int row, int column) {	
		return this.getColumnas().get( column ).getValue( this.getBlocks().get(row) );
	}	
}
