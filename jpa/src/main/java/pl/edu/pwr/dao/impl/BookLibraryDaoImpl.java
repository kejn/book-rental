package pl.edu.pwr.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.BookLibraryDao;
import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.entity.BookLibraryEntityId;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.entity.QBookLibraryEntity;
import pl.edu.pwr.exception.BookNotAvailableException;

@Component
public class BookLibraryDaoImpl extends AbstractDao<BookLibraryEntity, QBookLibraryEntity, BookLibraryEntityId>
    implements BookLibraryDao {

	@Autowired
	private BookDao bookDao;

	@Autowired
	private LibraryDao libraryDao;

	@Override
	protected void setQEntity() {
		qEntity = QBookLibraryEntity.bookLibraryEntity;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return found entity, new book_library entity if not found (book and
	 *         library cannot be null) or null otherwise
	 */
	@Override
	public BookLibraryEntity findOne(BookLibraryEntityId id) {
		BookLibraryEntity result = super.findOne(id);
		if (result == null) {
			BookEntity book = bookDao.findOne(id.getBook().getId());
			LibraryEntity library = libraryDao.findOne(id.getLibrary().getId());

			if (book != null && library != null) {
				result = new BookLibraryEntity(book, library, 0);
			}
		}
		return result;
	}

	@Override
	public List<BookLibraryEntity> findBookLibraryByLibrary(LibraryEntity library) {
		checkIfArgumentIsNull(library, "library");
		prepareQueryVariables();
		return query.from(qEntity).where(qEntity.library.eq(library)).list(qEntity);
	}

	@Override
	public List<BookLibraryEntity> findBookLibraryByBook(BookEntity book) {
		checkIfArgumentIsNull(book, "book");
		prepareQueryVariables();
		return query.from(qEntity).where(qEntity.book.eq(book)).list(qEntity);
	}

	@Override
	public void deleteBookLibraryByBook(BookEntity book) {
		checkIfArgumentIsNull(book, "book");
		List<BookLibraryEntity> references = new ArrayList<>(findBookLibraryByBook(book));
		for (BookLibraryEntity bookLibraryEntity: references) {
			delete(bookLibraryEntity);
		}
	}

	@Override
	public BookLibraryEntity addBookToLibrary(BookEntity book, LibraryEntity library) {
		checkIfArgumentIsNull(book, "book");
		checkIfArgumentIsNull(library, "library");

		final BookLibraryEntityId id = new BookLibraryEntityId(book, library);
		BookLibraryEntity bookLibrary = findOne(id);
		int quantityBefore = bookLibrary.getQuantity();
		bookLibrary.setQuantity(quantityBefore + 1);
		return update(bookLibrary);
	}

	@Override
	public BookLibraryEntity removeBookFromLibrary(BookEntity book, LibraryEntity library)
	    throws BookNotAvailableException {
		checkIfArgumentIsNull(book, "book");
		checkIfArgumentIsNull(library, "library");

		final BookLibraryEntityId id = new BookLibraryEntityId(book, library);
		BookLibraryEntity bookLibrary = findOne(id);

		if (!bookLibrary.isBookAvailable()) {
			throw new BookNotAvailableException();
		}

		int quantityBefore = bookLibrary.getQuantity();
		bookLibrary.setQuantity(quantityBefore - 1);
		return update(bookLibrary);
	}

}
