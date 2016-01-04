package pl.edu.pwr.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

import net.logstash.logback.encoder.org.apache.commons.lang.NullArgumentException;
import pl.edu.pwr.annotation.NullableId;
import pl.edu.pwr.common.IdAware;
import pl.edu.pwr.config.DataAccessConfig;
import pl.edu.pwr.dao.Dao;

/**
 * Abstract instance of Data Access Object.
 * 
 * @author KNIEMCZY
 *
 * @param <T>
 *          type of entity
 * @param
 *          <Q>
 *          generated Q-type of entity
 * @param <K>
 *          key type of entity just like for its ID member field
 */
@Transactional(Transactional.TxType.REQUIRED)
public abstract class AbstractDao<T extends IdAware<K>, Q extends EntityPathBase<?>, K extends Serializable>
    implements Dao<T, K> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);

	@PersistenceContext(unitName = DataAccessConfig.PERSISTANCE_UNIT_NAME)
	protected EntityManager entityManager;

	protected Class<T> domainClass;

	protected JPAQuery query;

	protected Q qEntity;

	/**
	 * Creates a new JPAQuery and initializes qEntity. Call this method before any
	 * <b>query.from(qEntity)</b> usage.
	 */
	protected void prepareQueryVariables() {
		query = new JPAQuery(entityManager);
		setQEntity();
	}

	/**
	 * Should set qEntity using generated QClass.Class public field.
	 */
	protected abstract void setQEntity();

	protected void checkIfArgumentIsNull(Object argument, String argumentName) {
		if (argument == null) {
			if (argumentName == null) {
				throw new NullArgumentException("argumentName");
			}
			throw new NullArgumentException(argumentName);
		}
	}

	/**
	 * Deletes entity from DB matching given <b>id</b>.
	 */
	@Override
	public void delete(K id) {
		entityManager.remove(getOne(id));
	}

	/**
	 * Deletes entity from DB matching given <b>entity</b> (by getting its id).
	 * {@link #update(IdAware)} is necessary to merge given entity into transaction.
	 */
	@Override
	public void delete(T entity) {
		entityManager.remove(update(entity));
	}

	@Override
	public boolean exists(K id) {
		return findOne(id) != null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		prepareQueryVariables();
		return (List<T>) query.from(qEntity).list(qEntity);
	}

	@Override
	public T findOne(K id) {
		return entityManager.find(getDomainClass(), id);
	}

	@Override
	public T getOne(K id) {
		return entityManager.getReference(getDomainClass(), id);
	}

	@Override
	@NullableId
	public T save(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		checkIfArgumentIsNull(entity, "entity");
		return entityManager.merge(entity);
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getDomainClass() {
		if (domainClass == null) {
			ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
			// [0] - T, [1] - Q, [2] - K
			domainClass = (Class<T>) type.getActualTypeArguments()[0];
			logger.debug("domainClass initialized with {}", domainClass.getName());
		}
		return domainClass;
	}

}
