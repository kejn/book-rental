package pl.edu.pwr.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T, K extends Serializable> {
	
	public void delete(K id);
	
	public void delete(T entity);
	
	public boolean exists(K id);

	public List<T> findAll();

	public T findOne(K id);
	
	public T getOne(K id);

	public T save(T entity);

	public T update(T entity);
}
