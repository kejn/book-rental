package pl.edu.pwr.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.exception.NotNullIdException;
import pl.edu.pwr.test.config.DataAccessDaoTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessDaoTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class LibraryDaoImplTest {

	@Autowired
	private LibraryDao libraryDao;
	
	@Test
	public void shouldFindLibraryById() {
		// given
		final BigDecimal id = BigDecimal.ONE;
		// when
		LibraryEntity library = libraryDao.findOne(id);
		// then
		assertNotNull(library);
	}
	
	@Test
	public void shouldFindLibraryByName(){
		// given
		final String libraryName = "we wrocławiu";
		// when
		List<LibraryEntity> libraries = libraryDao.findLibrariesByName(libraryName);
		// then
		assertNotNull(libraries);
		assertFalse(libraries.isEmpty());
		assertEquals("Biblioteka we Wrocławiu", libraries.get(0).getName());
	}
	
	@Test
	public void shouldSaveLibraryWithNullId(){
		// given
		LibraryEntity library = new LibraryEntity(null, "Biblioteka #1 do zapisania");
		// when
		library = libraryDao.save(library);
		// then
		assertNotNull(library);
		assertNotNull(library.getId());		
	}

	@Test(expected = NotNullIdException.class)
	public void shouldThrowNotNullIdExceptionOnLibrarySave(){
		// given
		LibraryEntity library = new LibraryEntity(BigDecimal.ONE, "Biblioteka #2 do zapisania");
		// when
		library = libraryDao.save(library);
	}

}
