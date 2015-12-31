package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

import net.logstash.logback.encoder.org.apache.commons.lang.NullArgumentException;
import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.QBookEntity;

@Component
public class BookDaoImpl extends AbstractDao<BookEntity, BigDecimal> implements BookDao {

	@Override
	public List<BookEntity> findBooksByTitle(String bookTitle) {
		if(bookTitle == null) {
			throw new NullArgumentException("bookTitle");
		}
		JPAQuery query = new JPAQuery(entityManager);
		QBookEntity bookEntity = QBookEntity.bookEntity;
		return query.from(bookEntity).where(bookEntity.title.containsIgnoreCase(bookTitle)).list(bookEntity);
	}

	@Override
	public List<BookEntity> findBooksByAuthor(String author) {
		if(author == null) {
			throw new NullArgumentException("author");
		}
		JPAQuery query = new JPAQuery(entityManager);
		QBookEntity bookEntity = QBookEntity.bookEntity;
		BooleanBuilder builder = new BooleanBuilder();
		builder.or(bookEntity.authors.any().firstName.startsWithIgnoreCase(author));
		builder.or(bookEntity.authors.any().lastName.startsWithIgnoreCase(author));
		return query.from(bookEntity).where(builder).list(bookEntity);
	}
	
}
