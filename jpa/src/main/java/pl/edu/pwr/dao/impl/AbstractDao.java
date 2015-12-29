package pl.edu.pwr.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import pl.edu.pwr.config.DataAccessConfig;
import pl.edu.pwr.dao.Dao;

@Transactional(Transactional.TxType.REQUIRED)
public abstract class AbstractDao<T, K extends Serializable> implements Dao<T, K> {

	@PersistenceContext(unitName = DataAccessConfig.PERSISTANCE_UNIT_NAME)
	protected EntityManager entityManager;
	
	protected Class<T> domainClass;
	
	@Override
	public T findOne(K id) {
		return entityManager.find(getDomainClass() , id);
	}
	
	@SuppressWarnings("unchecked")
	protected Class<T> getDomainClass() {
		if(domainClass == null) {
			ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
			domainClass = (Class<T>) type.getActualTypeArguments()[0]; // [0] - T, [1] - K
		}
		return domainClass;
	}

}
