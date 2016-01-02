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

	/**
	 * JPA query
	 */
	private JPAQuery query;

	/**
	 * Let create queries by choosing columns like BookEntity member fields.
	 */
	private QBookEntity bookEntity;

	private void prepareQueryVariables() {
		query = new JPAQuery(entityManager);
		bookEntity = QBookEntity.bookEntity;
	}

	@Override
	// @NotNullArg
	public List<BookEntity> findBooksByTitle(String bookTitle) {
		if (bookTitle == null) {
			throw new NullArgumentException("bookTitle");
		}
		prepareQueryVariables();
		return query.from(bookEntity).where(bookEntity.title.containsIgnoreCase(bookTitle)).list(bookEntity);
	}

	@Override
	// @NotNullArg
	public List<BookEntity> findBooksByAuthor(String author) {
		if (author == null) {
			throw new NullArgumentException("author");
		}
		prepareQueryVariables();
		BooleanBuilder builder = new BooleanBuilder();
		builder.or(bookEntity.authors.any().firstName.startsWithIgnoreCase(author));
		builder.or(bookEntity.authors.any().lastName.startsWithIgnoreCase(author));
		return query.from(bookEntity).where(builder).list(bookEntity);
	}

	@Override
	// @NotNullArg
	public List<BookEntity> findBooksByLibraryName(String libraryName) {
		if (libraryName == null) {
			throw new NullArgumentException("libraryName");
		}
		prepareQueryVariables();
		return query.from(bookEntity).where(bookEntity.libraries.any().name.containsIgnoreCase(libraryName))
				.list(bookEntity);
	}

}
