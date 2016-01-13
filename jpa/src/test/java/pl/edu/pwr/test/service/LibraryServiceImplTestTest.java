package pl.edu.pwr.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.mapper.impl.LibraryMapper;
import pl.edu.pwr.service.impl.LibraryServiceImpl;
import pl.edu.pwr.to.LibraryTo;

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
	public void shouldFindAllLibraries() {
		// when
		Mockito.when(libraryDao.findAll()).thenReturn(Arrays.asList(libraryMock));
		List<LibraryTo> libraries = new ArrayList<>(libraryService.findAll());
		// then
		Mockito.verify(libraryDao).findAll();
		assertNotNull(libraries);
		assertFalse(libraries.isEmpty());
	}
	
	@Test
	public void shouldFindLibraryById() {
		// given
		final BigDecimal id = BigDecimal.ONE;
		// when
		Mockito.when(libraryDao.findOne(Mockito.any(BigDecimal.class))).thenReturn(libraryMock);
		LibraryTo library = libraryService.findLibraryById(id);
		// then
		ArgumentCaptor<BigDecimal> captor = ArgumentCaptor.forClass(BigDecimal.class);
		Mockito.verify(libraryDao).findOne(captor.capture());
		assertNotNull(library);
	}
	
	@Test
	public void shouldFindLibraryByName() {
		// given
		final String libraryName = "we wrocławiu";
		// when
		Mockito.when(libraryDao.findLibrariesByName(libraryName)).thenReturn(Arrays.asList(libraryMock));
		List<LibraryTo> libraries = new ArrayList<>(libraryService.findLibrariesByName(libraryName));
		// then
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(libraryDao).findLibrariesByName(captor.capture());
		assertNotNull(libraries);
		assertFalse(libraries.isEmpty());
	}

}
