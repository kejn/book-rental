package pl.edu.pwr.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.entity.AuthorEntity;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.mapper.impl.AuthorMapper;
import pl.edu.pwr.mapper.impl.BookLibraryMapper;
import pl.edu.pwr.mapper.impl.BookMapper;
import pl.edu.pwr.mapper.impl.LibraryMapper;
import pl.edu.pwr.service.impl.BookServiceImpl;
import pl.edu.pwr.to.BookTo;

public class BookServiceImplTest {

	private BookMapper bookMapper = new BookMapper(new AuthorMapper(), new BookLibraryMapper(new LibraryMapper()));

	@Mock
	private BookDao bookDao;

	@InjectMocks
	private BookServiceImpl bookService;

	private BookEntity bookEntityMock() {
		AuthorEntity author1 = new AuthorEntity(new BigDecimal("1"), "Jan", "Kowalski");
		AuthorEntity author2 = new AuthorEntity(new BigDecimal("2"), "Jan", "Brzechwa");

		LibraryEntity library = new LibraryEntity(new BigDecimal("1"), "Biblioteka we Wrocławiu");

		BookEntity book = new BookEntity(new BigDecimal("1"), "Pierwsza książka");
		book.addAuthors(author1, author2);
		book.addLibrary(library, 1);

		return book;
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Whitebox.setInternalState(bookService, "bookMapper", bookMapper);
	}

	@Test
	public void shouldFindAllBooks() {
		// when
		Mockito.when(bookDao.findAll()).thenReturn(Arrays.asList(bookEntityMock()));
		List<BookTo> books = bookService.findAll();
		// then
		assertNotNull(books);
		assertFalse(books.isEmpty());
	}

	@Test
	public void shouldFindBookById() {
		// given
		final BigDecimal id = BigDecimal.ONE;
		// when
		Mockito.when(bookDao.findOne(Mockito.any(BigDecimal.class))).thenReturn(bookEntityMock());
		BookTo book = bookService.findBookById(id);
		// then
		ArgumentCaptor<BigDecimal> captor = ArgumentCaptor.forClass(BigDecimal.class);
		Mockito.verify(bookDao).findOne(captor.capture());
		assertNotNull(book);
	}

	@Test
	public void shouldFindBooksByTitle() {
		// given
		final String titleFragment = "pierwsza";
		// when
		Mockito.when(bookDao.findBooksByTitle(Mockito.anyString())).thenReturn(Arrays.asList(bookEntityMock()));
		List<BookTo> books = bookService.findBooksByTitle(titleFragment);
		// then
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(bookDao).findBooksByTitle(captor.capture());
		assertNotNull(books);
		assertFalse(books.isEmpty());
	}

	@Test
	public void shouldFindBooksByAuthorPrefixIgnorecase() {
		// given
		final String authorPrefix = "kowalski";
		// when
		Mockito.when(bookDao.findBooksByAuthor(Mockito.anyString())).thenReturn(Arrays.asList(bookEntityMock()));
		List<BookTo> books = bookService.findBooksByAuthor(authorPrefix);
		// then
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(bookDao).findBooksByAuthor(captor.capture());
		assertNotNull(books);
		assertFalse(books.isEmpty());
	}

	@Test
	public void shouldFindBooksByLibraryName() {
		// given
		final String libraryName = "wrocław";
		// when
		Mockito.when(bookDao.findBooksByLibraryName(Mockito.anyString())).thenReturn(Arrays.asList(bookEntityMock()));
		List<BookTo> books = bookService.findBooksByLibraryName(libraryName);
		// then
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(bookDao).findBooksByLibraryName(captor.capture());
		assertNotNull(books);
		assertFalse(books.isEmpty());
	}

	@Test
	public void shouldSaveBook() {
		// given
		final BigDecimal obtainedId = new BigDecimal("999");
		BookTo book = bookMapper.map2To(bookEntityMock());
		book.setId(null);
		// when
		Mockito.when(bookDao.save(Mockito.any(BookEntity.class))).thenAnswer(new Answer<BookEntity>() {
			@Override
			public BookEntity answer(InvocationOnMock invocation) throws Throwable {
				BookEntity param = invocation.getArgumentAt(0, BookEntity.class);
				param.setId(obtainedId);
				return param;
			}
		});
		book = bookService.save(book);
		// then
		ArgumentCaptor<BookEntity> captor = ArgumentCaptor.forClass(BookEntity.class);
		Mockito.verify(bookDao).save(captor.capture());
		assertNotNull(captor.getValue().getId());
		assertEquals(obtainedId, captor.getValue().getId());
	}

	@Test
	public void shouldFindBookByTitleUsingTitleAuthorLibrary() {
		// given
		final String title = "pierwsza";
		// when
		Mockito.when(bookDao.findBooksByTitleAuthorLibrary(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		    .thenReturn(Arrays.asList(bookEntityMock()));
	  final List<BookTo> books = bookService.findBooksByTitleAuthorLibrary(title, "", "");
	  // then
	  ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
	  Mockito.verify(bookDao).findBooksByTitleAuthorLibrary(captor.capture(), captor.capture(), captor.capture());
	  assertNotNull(books);
	  assertFalse(books.isEmpty());
	  assertEquals(title, captor.getAllValues().get(0));
	}

	@Test
	public void shouldFindBookByAuthorUsingTitleAuthorLibrary() {
		// given
		final String author = "kowalski";
		// when
		Mockito.when(bookDao.findBooksByTitleAuthorLibrary(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		.thenReturn(Arrays.asList(bookEntityMock()));
		final List<BookTo> books = bookService.findBooksByTitleAuthorLibrary("", author, "");
		// then
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(bookDao).findBooksByTitleAuthorLibrary(captor.capture(), captor.capture(), captor.capture());
		assertNotNull(books);
		assertFalse(books.isEmpty());
		assertEquals(author, captor.getAllValues().get(1));
	}

	@Test
	public void shouldFindBookByLibraryUsingTitleAuthorLibrary() {
		// given
		final String libraryName = "wrocław";
		// when
		Mockito.when(bookDao.findBooksByTitleAuthorLibrary(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		.thenReturn(Arrays.asList(bookEntityMock()));
		final List<BookTo> books = bookService.findBooksByTitleAuthorLibrary("", "", libraryName);
		// then
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(bookDao).findBooksByTitleAuthorLibrary(captor.capture(), captor.capture(), captor.capture());
		assertNotNull(books);
		assertFalse(books.isEmpty());
		assertEquals(libraryName, captor.getAllValues().get(2));
	}
}
