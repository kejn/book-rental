package pl.edu.pwr.service;

import java.math.BigDecimal;
import java.util.List;

import pl.edu.pwr.to.LibraryTo;

/**
 * Calls various LibraryDao methods and converts entities to transport objects.
 * 
 * @author KNIEMCZY
 *
 */
public interface LibraryService {
	
	public List<LibraryTo> findAll();
	
	public List<LibraryTo> findLibrariesByName(String libraryName);

	public LibraryTo findLibraryById(BigDecimal libraryId);

}
