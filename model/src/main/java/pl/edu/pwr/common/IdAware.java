package pl.edu.pwr.common;

import java.io.Serializable;

/**
 * Implies that class implementing this interface should contain <b>id</b>
 * member field.
 * 
 * @author KNIEMCZY
 *
 * @param <K>
 *            type of <b>id</b> member field
 */
public interface IdAware<K extends Serializable> {

	/**
	 * @return <b>id</b> member field
	 */
	public K getId();
}
