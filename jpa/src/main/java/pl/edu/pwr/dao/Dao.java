package pl.edu.pwr.dao;

import java.io.Serializable;

public interface Dao<T, K extends Serializable> {
	
	T findOne(K bookId);

}
