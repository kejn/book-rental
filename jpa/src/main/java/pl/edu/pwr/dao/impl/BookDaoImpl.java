package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.BookLibraryDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.QBookEntity;
import pl.edu.pwr.tool.StringCheck;

@Component
public class BookDaoImpl extends AbstractDao<BookEntity, QBookEntity, BigDecimal> implements BookDao {

	@Autowired
	private BookLibraryDao bookLibraryDao;

	@Override
	protected void setQEntity() {
		qEntity = QBookEntity.bookEntity;
	}

	/**
	 * {@inheritDoc} Additionaly removes binding between this book and referenced
	 * libraries within.
	 */
	@Override
	public void delete(BigDecimal id) {
		BookEntity book = findOne(id);
		bookLibraryDao.deleteBookLibraryByBook(book);
		super.delete(id);
	}

	/**
	 * {@inheritDoc} Additionaly removes binding between this book and referenced
	 * libraries within.
	 */
	@Override
	public void delete(BookEntity book) {
		bookLibraryDao.deleteBookLibraryByBook(book);
		super.delete(book);
	}

	private Predicate conditionTitleContainsIgnoreCase(String bookTitle) {
		return qEntity.title.containsIgnoreCase(bookTitle);
	}

	private Predicate conditionAuthorStartsWithIgnoreCase(String author) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.or(qEntity.authors.any().firstName.startsWithIgnoreCase(author));
		builder.or(qEntity.authors.any().lastName.startsWithIgnoreCase(author));
		return builder;
	}

	private Predicate conditionLibraryContainsIgnoreCase(String libraryName) {
		return qEntity.libraries.any().library.name.containsIgnoreCase(libraryName);
	}

	@Override
	public List<BookEntity> findBooksByTitle(String bookTitle) {
		checkIfArgumentIsNull(bookTitle, "bookTitle");
		prepareQueryVariables();
		return query.from(qEntity).where(conditionTitleContainsIgnoreCase(bookTitle)).list(qEntity);
	}

	@Override
	public List<BookEntity> findBooksByAuthor(String author) {
		checkIfArgumentIsNull(author, "author");
		prepareQueryVariables();
		return query.from(qEntity).where(conditionAuthorStartsWithIgnoreCase(author)).list(qEntity);
	}

	@Override
	public List<BookEntity> findBooksByLibraryName(String libraryName) {
		checkIfArgumentIsNull(libraryName, "libraryName");
		prepareQueryVariables();
		return query.from(qEntity).where(conditionLibraryContainsIgnoreCase(libraryName)).list(qEntity);
	}

	@Override
	public List<BookEntity> findBooksByTitleAuthorLibrary(String bookTitle, String bookAuthor, String bookLibrary) {
		prepareQueryVariables();
		BooleanBuilder builder = new BooleanBuilder();
		if (!StringCheck.stringIsNullOrEmpty(bookTitle)) {
			builder.and(conditionTitleContainsIgnoreCase(bookTitle));
		}
		if (!StringCheck.stringIsNullOrEmpty(bookAuthor)) {
			builder.and(conditionAuthorStartsWithIgnoreCase(bookAuthor));
		}
		if (!StringCheck.stringIsNullOrEmpty(bookLibrary)) {
			builder.and(conditionLibraryContainsIgnoreCase(bookLibrary));
		}
		return query.from(qEntity).where(builder).list(qEntity);
	}

}
