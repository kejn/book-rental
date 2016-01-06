package pl.edu.pwr.service;

import java.util.List;

import pl.edu.pwr.to.LibraryTo;

public interface LibraryService {
	
	public List<LibraryTo> findAll();
	
	public List<LibraryTo> findLibrariesByName(String libraryName);

}
