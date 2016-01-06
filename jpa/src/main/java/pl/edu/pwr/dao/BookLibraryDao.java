package pl.edu.pwr.dao;

import java.util.Collection;

import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.entity.BookLibraryEntityId;
import pl.edu.pwr.entity.LibraryEntity;

public interface BookLibraryDao extends Dao<BookLibraryEntity, BookLibraryEntityId> {

	/**
	 * Searches database for libraries matching given name.
	 * 
	 * @param libraryName
	 *          name of library to match search results.
	 * @return list of library entities found in database matching above criteria
	 *         or <b>null</b> otherwise.
	 */
	public Collection<BookLibraryEntity> findBookLibraryByLibraryName(String libraryName);
	
	public Collection<BookLibraryEntity> findBookLibraryByBook(BookEntity book);
	
	public void deleteBookLibraryByBook(BookEntity book);
	
	public BookLibraryEntity addBookToLibrary(BookEntity book, LibraryEntity library);
	
}
