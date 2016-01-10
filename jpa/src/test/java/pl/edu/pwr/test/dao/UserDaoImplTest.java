package pl.edu.pwr.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNotNull;

import java.math.BigDecimal;
import java.util.HashSet;

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
import pl.edu.pwr.exception.UserEmailExistsException;
import pl.edu.pwr.exception.UserNameExistsException;
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
		final int userBooksSizeBefore = user.getBooks().size();
		// when
		try {
			user = userDao.rentUserABook(user, book, library);
		} catch (BookAlreadyRentException | BookNotAvailableException e) {
			fail(e.getMessage());
		}
		bookLibraryEntity = bookLibraryDao.findOne(bookLibraryId);
		assumeNotNull(bookLibraryEntity);

		final int quantityAfter = bookLibraryEntity.getQuantity();
		final int userBooksSizeAfter = user.getBooks().size();
		// then
		assertNotNull(user);
		assertFalse(user.getBooks().isEmpty());
		assertEquals(1, user.getBooks().size());
		assertTrue(userBooksSizeBefore >= 0);
		assertTrue(userBooksSizeAfter >= 0);
		assertEquals(userBooksSizeBefore, userBooksSizeAfter - 1);
		assertTrue(quantityBefore >= 0);
		assertTrue(quantityAfter >= 0);
		assertEquals(quantityBefore, quantityAfter + 1);
	}

	/**
	 * !!! depends on {@link #shouldFindUserById()},
	 * {@link pl.edu.pwr.test.dao.BookDaoImplTest#shouldFindBookById()},
	 * {@link pl.edu.pwr.test.dao.LibraryDaoImplTest#shouldFindLibraryById()} and
	 * {@link pl.edu.pwr.test.dao.BookLibraryDaoImplTest#shouldFindBookLibraryByBookLibraryEntityId()}
	 */
	@Test(expected = BookAlreadyRentException.class)
	public void userCantRentAlreadyRentBook() throws BookAlreadyRentException {
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
		
		// when
		try {
			user = userDao.rentUserABook(user, book, library);
			user = userDao.rentUserABook(user, book, library);
		} catch (BookNotAvailableException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * !!! depends on {@link #shouldFindUserById()},
	 * {@link pl.edu.pwr.test.dao.BookDaoImplTest#shouldFindBookById()},
	 * {@link pl.edu.pwr.test.dao.LibraryDaoImplTest#shouldFindLibraryById()} and
	 * {@link pl.edu.pwr.test.dao.BookLibraryDaoImplTest#shouldFindBookLibraryByBookLibraryEntityId()}
	 */
	@Test(expected = BookNotAvailableException.class)
	public void userCantRentANotAvailableBook() throws BookNotAvailableException {
		// given
		final BigDecimal bookId = new BigDecimal("4");
		final BigDecimal libraryId = BigDecimal.ONE;
		final BigDecimal userId = BigDecimal.ONE;
		final BookLibraryEntityId bookLibraryId = new BookLibraryEntityId(bookId, libraryId);
		
		final BookEntity book = bookDao.findOne(bookId);
		final LibraryEntity library = libraryDao.findOne(libraryId);
		BookLibraryEntity bookLibraryEntity = bookLibraryDao.findOne(bookLibraryId);
		UserEntity user = userDao.findOne(userId);
		assumeNotNull(book,library,bookLibraryEntity,user);
		
		// when
		try {
			user = userDao.rentUserABook(user, book, library);
		} catch (BookAlreadyRentException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	!!! depends on {@link #shouldFindUserById()},
	 * {@link pl.edu.pwr.test.dao.BookDaoImplTest#shouldFindBookById()},
	 * {@link pl.edu.pwr.test.dao.LibraryDaoImplTest#shouldFindLibraryById()} and
	 * {@link pl.edu.pwr.test.dao.BookLibraryDaoImplTest#shouldFindBookLibraryByBookLibraryEntityId()}
	 */
	@Test
	public void userShouldReturnABookToLibrary() {
		// given
		final BigDecimal bookId = BigDecimal.ONE;
		final BigDecimal libraryId = new BigDecimal("3");
		final BigDecimal userId = new BigDecimal("2");
		final BookLibraryEntityId bookLibraryId = new BookLibraryEntityId(bookId, libraryId);

		final BookEntity book = bookDao.findOne(bookId);
		final LibraryEntity library = libraryDao.findOne(libraryId);
		UserEntity user = userDao.findOne(userId);
		BookLibraryEntity bookLibraryEntity = bookLibraryDao.findOne(bookLibraryId);
		assumeNotNull(book,bookLibraryEntity, library,user);
		
		final int quantityBefore = bookLibraryEntity.getQuantity();
		final int userBooksSizeBefore = user.getBooks().size();
		// when
		try {
			user = userDao.returnABookToLibrary(user, book, library);
		} catch (BookNotRentException e) {
			fail(e.getMessage());
		}
		bookLibraryEntity = bookLibraryDao.findOne(bookLibraryId);
		assumeNotNull(bookLibraryEntity);

		final int quantityAfter = bookLibraryEntity.getQuantity();
		final int userBooksSizeAfter = user.getBooks().size();
		// then
		assertNotNull(user);
		assertTrue(userBooksSizeBefore >= 0);
		assertTrue(userBooksSizeAfter >= 0);
		assertEquals(userBooksSizeBefore, userBooksSizeAfter + 1);
		assertTrue(quantityBefore >= 0);
		assertTrue(quantityAfter >= 0);
		assertEquals(quantityBefore, quantityAfter - 1);
	}

	/**
	!!! depends on {@link #shouldFindUserById()},
	 * {@link pl.edu.pwr.test.dao.BookDaoImplTest#shouldFindBookById()},
	 * {@link pl.edu.pwr.test.dao.LibraryDaoImplTest#shouldFindLibraryById()} and
	 * {@link pl.edu.pwr.test.dao.BookLibraryDaoImplTest#shouldFindBookLibraryByBookLibraryEntityId()}
	 */
	@Test(expected = BookNotRentException.class)
	public void userCantReturnNotRentBookToLibrary() throws BookNotRentException {
		// given
		final BigDecimal bookId = new BigDecimal("2");
		final BigDecimal libraryId = new BigDecimal("3");
		final BigDecimal userId = new BigDecimal("2");
		final BookLibraryEntityId bookLibraryId = new BookLibraryEntityId(bookId, libraryId);
		
		final BookEntity book = bookDao.findOne(bookId);
		final LibraryEntity library = libraryDao.findOne(libraryId);
		UserEntity user = userDao.findOne(userId);
		BookLibraryEntity bookLibraryEntity = bookLibraryDao.findOne(bookLibraryId);
		assumeNotNull(book,bookLibraryEntity, library,user);
		
		// when
		user = userDao.returnABookToLibrary(user, book, library);
	}
	
	@Test
	public void shouldCreateNewUser() {
		// given
		final String userName = "new User";
		final String password = "simplePassword";
		final String email = "newuser@book-rental.com";
		UserEntity userToCreate = new UserEntity(null, userName, password, email, new HashSet<>());
		
		// when
		try {
			userToCreate = userDao.createNewUser(userToCreate);
		} catch (UserNameExistsException | UserEmailExistsException e) {
			fail(e.getMessage());
		}
		
		// then
		assertNotNull(userToCreate);
		assertNotNull(userToCreate.getId());
		assertEquals(userName, userToCreate.getName());
		assertEquals(password, userToCreate.getPassword());
		assertEquals(email, userToCreate.getEmail());
		assertTrue(userToCreate.getBooks().isEmpty());
	}

	@Test(expected = UserNameExistsException.class)
	public void shouldThrowUserNameExistsExceptionWhenCreateNewUser() throws UserNameExistsException {
		// given
		final String userName = "user";
		final String password = "simplePassword";
		final String email = "newuser2@book-rental.com";
		UserEntity userToCreate = new UserEntity(null, userName, password, email, new HashSet<>());
		
		// when
		try {
			userToCreate = userDao.createNewUser(userToCreate);
		} catch (UserEmailExistsException e) {
			fail(e.getMessage());
		}
	}

	@Test(expected = UserEmailExistsException.class)
	public void shouldThrowUserWithGivenEmailExistsExceptionWhenCreateNewUser() throws UserEmailExistsException {
		// given
		final String userName = "new User2";
		final String password = "simplePassword";
		final String email = "newuser@book-rental.com";
		UserEntity userToCreate = new UserEntity(null, userName, password, email, new HashSet<>());
		
		// when
		try {
			userToCreate = userDao.createNewUser(userToCreate);
		} catch (UserNameExistsException e) {
			fail(e.getMessage());
		}

	}
}
