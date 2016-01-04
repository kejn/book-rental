package pl.edu.pwr.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.entity.AuthorEntity;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.mapper.impl.AuthorMapper;
import pl.edu.pwr.mapper.impl.BookMapper;
import pl.edu.pwr.mapper.impl.LibraryMapper;
import pl.edu.pwr.service.impl.BookServiceImpl;
import pl.edu.pwr.to.BookTo;

public class BookServiceImplTest {

	private BookMapper bookMapper = new BookMapper(new AuthorMapper(), new LibraryMapper());

	@Mock
	private BookDao bookDao;

	@InjectMocks
	private BookServiceImpl bookService;

	private BookEntity bookEntityMock() {
		AuthorEntity author1 = new AuthorEntity(new BigDecimal("1"), "Jan", "Kowalski");
		AuthorEntity author2 = new AuthorEntity(new BigDecimal("2"), "Jan", "Brzechwa");
		Set<AuthorEntity> authors = new HashSet<>(Arrays.asList(author1, author2));
		
		LibraryEntity library = new LibraryEntity(new BigDecimal("1"), "Biblioteka we Wrocławiu");
		Set<LibraryEntity> libraries = new HashSet<>(Arrays.asList(library));
		
		return new BookEntity(new BigDecimal("1"), "Pierwsza książka", authors, libraries);
	}
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Whitebox.setInternalState(bookService, "bookMapper", bookMapper);
	}

	@Test
	public void testShouldFindBooksByTitle() {
		// given
		final String titleFragment = "pierwsza";
		// when
		Mockito.when(bookDao.findBooksByTitle(Mockito.anyString())).thenReturn(Arrays.asList(bookEntityMock()));
		List<BookTo> books = bookService.findBooksByTitle(titleFragment);
		// then
		assertNotNull(books);
		assertFalse(books.isEmpty());
		assertEquals("Pierwsza książka", books.get(0).getTitle());
	}

}
