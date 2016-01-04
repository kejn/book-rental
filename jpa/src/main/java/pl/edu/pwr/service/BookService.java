package pl.edu.pwr.service;

import java.util.List;

import pl.edu.pwr.to.BookTo;

public interface BookService {

	public List<BookTo> findBooksByTitle(String bookTitle);
	
}
