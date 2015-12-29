package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import pl.edu.pwr.config.DataAccessConfig;
import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.entity.BookEntity;

@Repository
@Transactional(Transactional.TxType.REQUIRED)
public class BookDaoImpl implements BookDao {

	@PersistenceContext(unitName = DataAccessConfig.PERSISTANCE_UNIT_NAME)
	private EntityManager entityManager;
	
	private Class<BookEntity> domainClass;

	@Override
	public BookEntity findOne(BigDecimal bookId) {
		return entityManager.find(getDomainClass() , bookId);
	}

	private Class<BookEntity> getDomainClass() {
		if(domainClass == null) {
			domainClass = BookEntity.class;
		}
		return domainClass;
	}

}
