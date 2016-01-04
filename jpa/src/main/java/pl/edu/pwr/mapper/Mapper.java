package pl.edu.pwr.mapper;

import java.util.Collection;

/**
 * Provides mapping from Transport Object types to Entities
 * @author KNIEMCZY
 *
 * @param <T> Transport Object type
 * @param <E> Entity type
 */
public interface Mapper<T, E> {

	public T map2To(E entity);
	
	public E map2Entity(T to);
	
	public Collection<T> map2To(Collection<E> entities);

	public Collection<E> map2Entity(Collection<T> tos);
}
