package pl.edu.pwr.dao;

import java.math.BigDecimal;
import java.util.List;

import pl.edu.pwr.entity.LibraryEntity;

public interface LibraryDao extends Dao<LibraryEntity, BigDecimal> {

	/**
	 * Searches database for libraries matching given name.
	 * @param libraryName name of library to match search results. 
	 * @return list of library entities found in database matching above criteria.
	 */
	public List<LibraryEntity> findLibrariesByName(String libraryName);

}
