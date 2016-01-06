package pl.edu.pwr.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.BookLibraryDao;
import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.entity.BookLibraryEntityId;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.test.config.DataAccessTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class BookLibraryDaoImplTest {

	@Autowired
	private BookLibraryDao bookLibraryDao;

	@Autowired
	private BookDao bookDao;

	@Autowired
	private LibraryDao libraryDao;

	@Test
	public void shouldFindBookLibraryByBookLibraryEntityId() {
		// given
		final BigDecimal bookId = BigDecimal.ONE;
		final BigDecimal libraryId = BigDecimal.ONE;
		final BookLibraryEntityId id = new BookLibraryEntityId(new BookEntity(bookId, null), new LibraryEntity(libraryId, null));
		// when
		BookLibraryEntity library = bookLibraryDao.findOne(id);
		// then
		assertNotNull(library);
	}

	@Test
	public void shouldFindLibraryByName() {
		// given
		final String libraryName = "we wrocławiu";
		// when
		List<BookLibraryEntity> libraries = new ArrayList<>(bookLibraryDao.findBookLibraryByLibraryName(libraryName));
		// then
		assertNotNull(libraries);
		assertFalse(libraries.isEmpty());
		assertEquals("Biblioteka we Wrocławiu", libraries.get(0).getLibrary().getName());
	}

	@Test
	public void shouldAddBookToLibrary() {
		// given
		final BigDecimal bookId = BigDecimal.ONE;
		final BigDecimal libraryId = BigDecimal.ONE;
		final BookLibraryEntityId id = new BookLibraryEntityId(new BookEntity(bookId, null), new LibraryEntity(libraryId, null));

		final LibraryEntity library = libraryDao.findOne(libraryId);
		final BookEntity book = bookDao.findOne(bookId);
		
		BookLibraryEntity bookLibrary = bookLibraryDao.findOne(id);
		final int quantityBefore = bookLibrary.getQuantity();
		// when
		bookLibrary = bookLibraryDao.addBookToLibrary(book, library);
		// then
		final int quantityAfter = bookLibrary.getQuantity();
		assertNotNull(bookLibrary);
		assertTrue(quantityBefore >= 0);
		assertTrue(quantityAfter >= 0);
		assertEquals(quantityAfter, quantityBefore + 1);
	}

	@Test
	public void shouldFindAllLibraries() {
		// when
		List<BookLibraryEntity> libraries = new ArrayList<>(bookLibraryDao.findAll());
		// then
		assertNotNull(libraries);
		assertFalse(libraries.isEmpty());
	}

}
