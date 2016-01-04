package pl.edu.pwr.service;

import java.util.List;

import pl.edu.pwr.to.BookTo;

public interface BookService {

	public List<BookTo> findAll();
	
	public List<BookTo> findBooksByTitle(String bookTitle);
	
	public List<BookTo> findBooksByAuthor(String author);

	public List<BookTo> findBooksByLibraryName(String libraryName);

	public BookTo save(BookTo book);
	
}
