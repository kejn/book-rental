package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysema.query.BooleanBuilder;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.BookLibraryDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.entity.QBookEntity;

@Component
public class BookDaoImpl extends AbstractDao<BookEntity, QBookEntity, BigDecimal> implements BookDao {

	@Autowired
	private BookLibraryDao bookLibraryDao;
	
	@Override
	protected void setQEntity() {
		qEntity = QBookEntity.bookEntity;
	}
	
	@Override
	public void delete(BigDecimal id) {
		BookEntity book = findOne(id);
		bookLibraryDao.deleteBookLibraryByBook(book);
		super.delete(id);
	}
	
	@Override
	public void delete(BookEntity book) {
		bookLibraryDao.deleteBookLibraryByBook(book);
		super.delete(book);
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
		return query.from(qEntity).where(qEntity.libraries.any().library.name.containsIgnoreCase(libraryName))
		    .list(qEntity);
	}

	// @Override
	// public boolean bookIsAvailable(BookEntity book, LibraryEntity library) {
	// checkIfArgumentIsNull(book, "book");
	// checkIfArgumentIsNull(library, "library");
	// prepareQueryVariables();
	// BooleanBuilder builder = new BooleanBuilder();
	// builder.and(qEntity.eq(book));
	// builder.and(qEntity.libraries.contains(library));
	// long count = query.from(qEntity).where(builder).count();
	// return count > 0;
	// }

}
