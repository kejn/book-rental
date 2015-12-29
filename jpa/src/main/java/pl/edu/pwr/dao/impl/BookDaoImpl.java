package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.entity.BookEntity;

@Repository
public class BookDaoImpl extends AbstractDao<BookEntity, BigDecimal> implements BookDao {

}
