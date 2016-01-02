package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.QBookEntity;

@Component
public class BookDaoImpl extends AbstractDao<BookEntity, QBookEntity, BigDecimal> implements BookDao {

	@Override
	protected void prepareQueryVariables() {
		query = new JPAQuery(entityManager);
		qEntity = QBookEntity.bookEntity;
	}

	@Override
	public List<BookEntity> findBooksByTitle(String bookTitle) {
		checkIfArgumentIsNull(bookTitle, "bookTitle");
		prepareQueryVariables();
		return query.from(qEntity).where(qEntity.title.containsIgnoreCase(bookTitle)).list(qEntity);
	}

	@Override
	public List<BookEntity> findBooksByAuthor(String author) {
		checkIfArgumentIsNull(author, "author");
		prepareQueryVariables();
		BooleanBuilder builder = new BooleanBuilder();
		builder.or(qEntity.authors.any().firstName.startsWithIgnoreCase(author));
		builder.or(qEntity.authors.any().lastName.startsWithIgnoreCase(author));
		return query.from(qEntity).where(builder).list(qEntity);
	}

	@Override
	public List<BookEntity> findBooksByLibraryName(String libraryName) {
		checkIfArgumentIsNull(libraryName, "libraryName");
		prepareQueryVariables();
		return query.from(qEntity).where(qEntity.libraries.any().name.containsIgnoreCase(libraryName))
				.list(qEntity);
	}

}
