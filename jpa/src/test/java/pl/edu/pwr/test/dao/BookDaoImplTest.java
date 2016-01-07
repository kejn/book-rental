package pl.edu.pwr.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.entity.AuthorEntity;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.exception.NotNullIdException;
import pl.edu.pwr.test.config.DataAccessTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class BookDaoImplTest {

	@Autowired
	private BookDao bookDao;

	@Test
	public void shouldFindBookById() {
		// given
		final BigDecimal bookId = BigDecimal.ONE;
		// when
		BookEntity bookEntity = bookDao.findOne(bookId);
		// then
		assertNotNull(bookEntity);
	}

	@Test
	public void shouldExistBookWithGivenId() {
		// given
		final BigDecimal bookId = BigDecimal.ONE;
		// when
		boolean exists = bookDao.exists(bookId);
		// then
		assertTrue(exists);
	}

	@Test
	public void shouldFindBookByTitle() {
		// given
		final String titleFragment = "pierwsza";
		// when
		List<BookEntity> books = new ArrayList<>(bookDao.findBooksByTitle(titleFragment));
		// then
		assertNotNull(books);
		assertFalse(books.isEmpty());
		assertEquals("Pierwsza książka", books.get(0).getTitle());
	}

	@Test
	public void shouldFindBookByAuthorPrefixIgnoreCase() {
		// given
		final String authorPrefix = "kowalski";
		// when
		List<BookEntity> books = new ArrayList<>(bookDao.findBooksByAuthor(authorPrefix));
		// then
		assertFalse(books.isEmpty());
		final Set<AuthorEntity> authors = books.get(0).getAuthors();
		assertNotNull(authors);
		assertTrue(authors.stream().anyMatch(author -> author.matchFirstOrLastName(authorPrefix)));
	}

	@Test
	public void shouldSaveBookWithNullId() {
		// given
		final AuthorEntity author = new AuthorEntity(null, "Adam", "Pawlak");
		final LibraryEntity library = new LibraryEntity(null, "Biblioteka #1 do zapisania z książką");
		BookEntity book = new BookEntity(null, "Książka do zapisania z id=null");
		book.addAuthors(author);
		book.addLibrary(library, 1);
		// when
		book = bookDao.save(book);
		// then
		assertNotNull(book);
		assertNotNull(book.getId());
	}

	@Test(expected = NotNullIdException.class)
	public void shouldThrowNotNullIdExceptionOnBookSave() {
		// given
		final AuthorEntity author = new AuthorEntity(null, "Tomasz", "Mazur");
		final LibraryEntity library = new LibraryEntity(null, "Biblioteka #2 do zapisania z książką");
		BookEntity book = new BookEntity(BigDecimal.ONE, "Książka do zapisania z id!=null");
		book.addAuthors(author);
		book.addLibrary(library, 1);
		// when
		book = bookDao.save(book);
	}

	/**
	 * !!! depends on {@link #shouldFindBookById()}
	 */
	@Test
	public void shouldDeleteBookById() {
		// given
		final BigDecimal id = new BigDecimal("2");
		// when
		bookDao.delete(id);
		boolean bookExistsInDatabase = bookDao.exists(id);
		// then
		assertFalse(bookExistsInDatabase);
	}

	/**
	 * !!! depends on {@link #shouldExistBookWithGivenId()}
	 */
	@Test
	public void shouldDeleteBook() {
		// given
		final BigDecimal id = new BigDecimal("3");
		final BookEntity book = bookDao.findOne(id);
		assumeNotNull(book);
		// when
		bookDao.delete(book);
		boolean bookExistsInDatabase = bookDao.exists(id);
		// then
		assertFalse(bookExistsInDatabase);
	}

	@Test
	public void shouldFindBooksByLibraryName() {
		// given
		final String libraryName = "wrocław";
		// when
		List<BookEntity> books = new ArrayList<>(bookDao.findBooksByLibraryName(libraryName));
		// then
		assertNotNull(books);
		assertFalse(books.isEmpty());
		
		final BookEntity bookToValidate = books.get(0);
		assertNotNull(bookToValidate.getLibraries());
		
		final Set<BookLibraryEntity> libraryToValidate = bookToValidate.getLibraries();
		assertTrue(libraryToValidate.stream().anyMatch(l -> l.getLibrary().getName().equals("Biblioteka we Wrocławiu")));
	}

}
