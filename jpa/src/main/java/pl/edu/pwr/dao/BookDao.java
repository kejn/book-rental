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
	 * @return list of book entities found in database matching above criteria or
	 *         <b>null</b> otherwise.
	 */
	public Collection<BookEntity> findBooksByTitle(String bookTitle);

	/**
	 * Searches database for books matching given author name and/or surname.
	 * 
	 * @param author
	 *          authors name and/or surname to match search results
	 * @return list of book entities found in database matching above criteria or
	 *         <b>null</b> otherwise.
	 */
	public Collection<BookEntity> findBooksByAuthor(String author);

	/**
	 * Searches database for books matching given library name.
	 * 
	 * @param libraryName
	 *          library name to match search results
	 * @return list of book entities found in database matching above criteria or
	 *         <b>null</b> otherwise.
	 */
	public Collection<BookEntity> findBooksByLibraryName(String libraryName);
	
}
