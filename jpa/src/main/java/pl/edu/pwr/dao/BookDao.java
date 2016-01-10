package pl.edu.pwr.dao;

import java.math.BigDecimal;
import java.util.Collection;

import pl.edu.pwr.entity.BookEntity;

public interface BookDao extends Dao<BookEntity, BigDecimal> {

	/**
	 * Searches database for books matching given title.
	 * 
	 * @param bookTitle
	 *          title of book to match search results
	 * @return collection of book entities found in database matching above
	 *         criteria or <b>null</b> otherwise.
	 */
	public Collection<BookEntity> findBooksByTitle(String bookTitle);

	/**
	 * Searches database for books matching given author name and/or surname.
	 * 
	 * @param author
	 *          authors name and/or surname to match search results
	 * @return collection of book entities found in database matching above
	 *         criteria or <b>null</b> otherwise.
	 */
	public Collection<BookEntity> findBooksByAuthor(String author);

	/**
	 * Searches database for books matching given library name.
	 * 
	 * @param libraryName
	 *          library name to match search results
	 * @return collection of book entities found in database matching above
	 *         criteria or <b>null</b> otherwise.
	 */
	public Collection<BookEntity> findBooksByLibraryName(String libraryName);

	/**
	 * Searches database for books matching given title, author and library name.
	 * 
	 * @param bookTitle
	 *          book title to match search results
	 * @param bookAuthor
	 *          author first or last name to match search results
	 * @param bookLibrary
	 *          library name to match search results
	 * @return collection of book entities found in database matching above
	 *         criteria or <b>null</b> otherwise.
	 */
	public Collection<BookEntity> findBooksByTitleAuthorLibrary(String bookTitle, String bookAuthor, String bookLibrary);

}
