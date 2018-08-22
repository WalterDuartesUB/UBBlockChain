package ar.edu.ub.seginfo.model;

public interface IBlock {
	String  getPreviousHash();
	String	getHash();
	boolean isValidBlock();
}
