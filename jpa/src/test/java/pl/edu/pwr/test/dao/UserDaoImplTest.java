package pl.edu.pwr.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.*;


import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.BookLibraryDao;
import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.entity.BookLibraryEntityId;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.exception.BookAlreadyRentException;
import pl.edu.pwr.exception.BookNotAvailableException;
import pl.edu.pwr.exception.BookNotRentException;
import pl.edu.pwr.test.config.DataAccessTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserDaoImplTest {

	@Autowired
	private UserDao userDao;

	@Autowired
	private BookDao bookDao;

	@Autowired
	private LibraryDao libraryDao;

	@Autowired
	private BookLibraryDao bookLibraryDao;

	@Test
	public void shouldFindUserById() {
		// given
		final BigDecimal id = BigDecimal.ONE;
		// when
		UserEntity user = userDao.findOne(id);
		// then
		assertNotNull(user);
	}

	@Test
	public void shouldFindUserEqualToNameIgnoreCaseAndVerifyPasswordCaseSensitive() {
		// given
		final String userName = "User";
		final String password = "sup3R$ecre7P4$$word";
		// when
		UserEntity user = userDao.findUserEqualToNameVerifyPassword(userName, password);
		// then
		assertNotNull(user);
		assertEquals(userName.toLowerCase(), user.getName().toLowerCase());
		assertEquals(password, user.getPassword());
	}

	/**
	 * !!! depends on {@link #shouldFindUserById()},
	 * {@link pl.edu.pwr.test.dao.BookDaoImplTest#shouldFindBookById()},
	 * {@link pl.edu.pwr.test.dao.LibraryDaoImplTest#shouldFindLibraryById()} and
	 * {@link pl.edu.pwr.test.dao.BookLibraryDaoImplTest#shouldFindBookLibraryByBookLibraryEntityId()}
	 * 
	 */
	@Test
	public void userShouldRentABook() {
		// given
		final BigDecimal bookId = BigDecimal.ONE;
		final BigDecimal libraryId = BigDecimal.ONE;
		final BigDecimal userId = BigDecimal.ONE;
		final BookLibraryEntityId bookLibraryId = new BookLibraryEntityId(bookId, libraryId);

		final BookEntity book = bookDao.findOne(bookId);
		final LibraryEntity library = libraryDao.findOne(libraryId);
		BookLibraryEntity bookLibraryEntity = bookLibraryDao.findOne(bookLibraryId);
		UserEntity user = userDao.findOne(userId);
		assumeNotNull(book,library,bookLibraryEntity,user);
		
		final int quantityBefore = bookLibraryEntity.getQuantity();
		// when
		try {
			user = userDao.rentUserABook(user, book, library);
		} catch (BookAlreadyRentException | BookNotAvailableException e) {
			fail(e.getMessage());
		}
		bookLibraryEntity = bookLibraryDao.findOne(bookLibraryId);
		assumeNotNull(bookLibraryEntity);
		final int quantityAfter = bookLibraryEntity.getQuantity();

		// then
		assertNotNull(user);
		assertFalse(user.getBooks().isEmpty());
		assertEquals(1, user.getBooks().size());
		assertTrue(quantityBefore >= 0);
		assertTrue(quantityAfter >= 0);
		assertEquals(quantityBefore, quantityAfter + 1);
	}

	@Test
	public void userShouldReturnABookToLibrary() {
		// given
		final BigDecimal bookId = BigDecimal.ONE;
		final BigDecimal libraryId = BigDecimal.ONE;
		final BigDecimal userId = new BigDecimal("2");
		final BookLibraryEntityId bookLibraryId = new BookLibraryEntityId(bookId, libraryId);

		final BookEntity book = bookDao.findOne(bookId);
		final LibraryEntity library = libraryDao.findOne(libraryId);
		BookLibraryEntity bookLibraryEntity = bookLibraryDao.findOne(bookLibraryId);
		UserEntity user = userDao.findOne(userId);
		assumeNotNull(book,library,bookLibraryEntity,user);
		
		final int quantityBefore = bookLibraryEntity.getQuantity();
		// when
		try {
			user = userDao.returnABookToLibrary(user, book, library);
		} catch (BookNotRentException e) {
			fail(e.getMessage());
		}
		bookLibraryEntity = bookLibraryDao.findOne(bookLibraryId);
		assumeNotNull(bookLibraryEntity);
		final int quantityAfter = bookLibraryEntity.getQuantity();
		// then
		assertNotNull(user);
		assertFalse(user.getBooks().isEmpty());
		assertEquals(1, user.getBooks().size());
		assertTrue(quantityBefore >= 0);
		assertTrue(quantityAfter >= 0);
		assertEquals(quantityBefore, quantityAfter - 1);
	}
}
