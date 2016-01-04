package pl.edu.pwr.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.mapper.impl.LibraryMapper;
import pl.edu.pwr.service.impl.LibraryServiceImpl;

public class LibraryServiceImplTestTest {

	private LibraryMapper libraryMapper = new LibraryMapper();

	@Mock
	private LibraryDao libraryDao;

	@InjectMocks
	private LibraryServiceImpl libraryService;

	private final LibraryEntity libraryMock = new LibraryEntity(new BigDecimal("1"), "Biblioteka we Wrocławiu");
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Whitebox.setInternalState(libraryService, "libraryMapper", libraryMapper);
	}
	
	@Test
	public void shouldFindLibraryByName() {
		// given
		final String libraryName = "we wrocławiu";
		// when
		Mockito.when(libraryDao.findLibrariesByName(libraryName)).thenReturn(Arrays.asList(libraryMock));
		List<LibraryEntity> libraries = new ArrayList<>(libraryDao.findLibrariesByName(libraryName));
		// then
		assertNotNull(libraries);
		assertFalse(libraries.isEmpty());
	}

}
