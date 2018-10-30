package ar.edu.ub.seginfo.exception;

import java.sql.Timestamp;

import ar.edu.ub.seginfo.model.IBlockFields;

public class BlockAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IBlockFields block;

	public BlockAlreadyExistsException(String msg, IBlockFields block) {
		super(msg);
		this.setBlock(block);
	}

	private IBlockFields getBlock() {
		return block;
	}

	private void setBlock(IBlockFields block) {
		this.block = block;
	}

	@Override
	public String getMessage() {
		return String.format("%s [fecha del bloque: %s]", super.getMessage(),
				new Timestamp(this.getBlock().getTimeStamp()).toString());
	}
}
