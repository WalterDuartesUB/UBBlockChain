package ar.edu.ub.seginfo.view;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import ar.edu.ub.seginfo.model.IBlock;
import ar.edu.ub.seginfo.model.IBlockChain;
import ar.edu.ub.seginfo.model.IBlockFields;
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
	private class ModelColumnTablePreviousHash extends ModelColumnTable<IBlockFields>{

		public ModelColumnTablePreviousHash(String nombre) {
			super(nombre);
		}

		@Override
		public Object getValue(IBlockFields data) {
			return data.getPreviousHash();
		}
		
	}

	//TODO Esto deberia estar en otro paquete
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
	private IBlockChain<IBlockFields> model;
	private Map<Integer, ModelColumnTable<IBlockFields>> columnas;
	
	public TableModelBlockChain(IBlockChain<IBlockFields> model) {
		this.setModel(model);
		
		this.setColumnas( new HashMap<Integer, ModelColumnTable<IBlockFields>>());
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

	private IBlockChain<IBlockFields> getModel() {
		return model;
	}

	private void setModel(IBlockChain<IBlockFields> model) {
		this.model = model;
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
		
		if( this.getModel() == null )
			return null;
		
		LinkedList<IBlockFields> blocks = new LinkedList<IBlockFields>();
		
		//TODO la carga de la lista del modelo deberia estar solo en el update de la ventana por parte del controlador
		this.getModel().getAll(blocks);
		
		return blocks;
	}

	@Override
	public Object getValueAt(int row, int column) {	
		return this.getColumnas().get( column ).getValue( this.getBlocks().get(row) );
	}	
}
