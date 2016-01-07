package pl.edu.pwr.dao;

import java.util.Collection;

import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.entity.BookLibraryEntityId;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.exception.BookNotAvailableException;

public interface BookLibraryDao extends Dao<BookLibraryEntity, BookLibraryEntityId> {

	/**
	 * Searches database for book_library bindings referencing <b>library</b>
	 * 
	 * @param library
	 *          reference to match search results.
	 * @return collection of library entities found in database matching above
	 *         criteria or <b>null</b> otherwise.
	 */
	public Collection<BookLibraryEntity> findBookLibraryByLibrary(LibraryEntity library);

	/**
	 * Searches database for book_library bindings referencing <b>book</b>
	 * 
	 * @param book
	 *          reference to match search results
	 * @return collection of library entities found in database matching above
	 *         criteria or <b>null</b> otherwise.
	 */
	public Collection<BookLibraryEntity> findBookLibraryByBook(BookEntity book);

	/**
	 * Removes book_library bindings referencing <b>book</b>
	 * 
	 * @param book
	 *          reference to match removed bindings
	 */
	public void deleteBookLibraryByBook(BookEntity book);

	/**
	 * Updates book_library bindings in database with new one setting its quantity
	 * to 1 or increases by 1 quantity of a binding if it already exists.
	 * 
	 * @param book
	 *          reference to book
	 * @param library
	 *          reference to library
	 * @return updated book_library binding entity
	 */
	public BookLibraryEntity addBookToLibrary(BookEntity book, LibraryEntity library);

	/**
	 * Updates book_library bindings in database with new one decreasing by 1
	 * quantity of a binding if it already exists.
	 * 
	 * @param book
	 *          reference to book
	 * @param library
	 *          reference to library
	 * @return updated book_library binding entity
	 * @throws BookNotAvailableException if quantity before method was called was equal to 0
	 */
	public BookLibraryEntity removeBookFromLibrary(BookEntity book, LibraryEntity library)
	    throws BookNotAvailableException;

}
