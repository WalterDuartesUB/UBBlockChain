package ar.edu.ub.seginfo.model;

public interface IRepository<T> {
	public boolean add( T dato );

	public int getCount();

	public String getLastHash();
}
