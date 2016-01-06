package pl.edu.pwr.dao;

import java.io.Serializable;
import java.util.Collection;

public interface Dao<T, K extends Serializable> {

	/**
	 * Remove entity from database matching given <b>id</b>.
	 * 
	 * @param id
	 *          id of entity to be deleted from database
	 */
	public void delete(K id);

	/**
	 * Remove entity from database matching given <b>entity</b>.
	 * 
	 * @param entity
	 *          entity to be deleted from database
	 */
	public void delete(T entity);

	/**
	 * Checks if entity with given <b>id</b> exists in database.
	 * 
	 * @param id
	 *          id of entity to be checked
	 * @return <b>true</b> if such entity exists, <b>false</b> otherwise
	 */
	public boolean exists(K id);

	/**
	 * @return all entities of type <T> found in database
	 */
	public Collection<T> findAll();

	/**
	 * Searches database for entity matching given <b>id</b>.
	 * 
	 * @param id
	 *          id to match search result
	 * @return found entity or null
	 */
	public T findOne(K id);

	/**
	 * Searches database for entity matching given <b>id</b>.
	 * 
	 * @param id
	 *          id to match search result
	 * @return reference to found entity or null
	 */
	public T getOne(K id);

	/**
	 * Saves <b>entity</b> in database.
	 * 
	 * @param entity
	 *          entity to be saved in database
	 * @return entity with updated <b>id</b> member field
	 */
	public T save(T entity);

	/**
	 * Updates existing <b>entity</b> in database or saves a new one if it didn't
	 * exist before.
	 * 
	 * @param entity
	 *          entity to be updated
	 * @return updated entity
	 */
	public T update(T entity);

}
