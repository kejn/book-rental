package pl.edu.pwr.service;

import java.util.List;

import pl.edu.pwr.to.BookTo;
import pl.edu.pwr.to.LibraryTo;

public interface LibraryService {
	
	public List<BookTo> findAll();
	
	public List<LibraryTo> findLibrariesByName(String libraryName);

}
