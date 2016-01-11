package pl.edu.pwr.service;

import java.math.BigDecimal;
import java.util.List;

import pl.edu.pwr.to.BookTo;

/**
 * Calls various BookDao methods and converts entities to transport objects.
 * 
 * @author KNIEMCZY
 *
 */
public interface BookService {

	public List<BookTo> findAll();
	
	public BookTo findBookById(BigDecimal id);

	public List<BookTo> findBooksByTitle(String bookTitle);

	public List<BookTo> findBooksByAuthor(String author);

	public List<BookTo> findBooksByLibraryName(String libraryName);

	public BookTo save(BookTo book);

	public List<BookTo> findBooksByTitleAuthorLibrary(String bookTitle, String bookAuthor, String bookLibrary);

}
