package pl.edu.pwr.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.AuthorEntity;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.entity.UserBookLibraryEntity;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.exception.BookAlreadyRentException;
import pl.edu.pwr.exception.BookNotAvailableException;
import pl.edu.pwr.exception.BookNotRentException;
import pl.edu.pwr.exception.UserEmailExistsException;
import pl.edu.pwr.exception.UserNameExistsException;
import pl.edu.pwr.mapper.impl.AuthorMapper;
import pl.edu.pwr.mapper.impl.BookLibraryMapper;
import pl.edu.pwr.mapper.impl.BookMapper;
import pl.edu.pwr.mapper.impl.LibraryMapper;
import pl.edu.pwr.mapper.impl.UserBookLibraryMapper;
import pl.edu.pwr.mapper.impl.UserMapper;
import pl.edu.pwr.service.impl.UserServiceImpl;
import pl.edu.pwr.to.BookTo;
import pl.edu.pwr.to.LibraryTo;
import pl.edu.pwr.to.UserTo;

public class UserServiceImplTest {

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserServiceImpl userService;

	private LibraryMapper libraryMapper = new LibraryMapper();

	private BookMapper bookMapper = new BookMapper(new AuthorMapper(), new BookLibraryMapper(libraryMapper));

	private UserBookLibraryMapper userBookLibraryMapper = new UserBookLibraryMapper(bookMapper, libraryMapper);

	private UserMapper userMapper = new UserMapper(userBookLibraryMapper);

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Whitebox.setInternalState(userService, "userMapper", userMapper);
		Whitebox.setInternalState(userService, "bookMapper", bookMapper);
		Whitebox.setInternalState(userService, "libraryMapper", libraryMapper);
	}

	private LibraryEntity libraryEntityMock() {
		return new LibraryEntity(new BigDecimal("1"), "Biblioteka we Wrocławiu");
	}

	private BookEntity bookEntityMock(LibraryEntity library) {
		AuthorEntity author1 = new AuthorEntity(new BigDecimal("1"), "Jan", "Kowalski");
		AuthorEntity author2 = new AuthorEntity(new BigDecimal("2"), "Jan", "Brzechwa");

		BookEntity book = new BookEntity(new BigDecimal("1"), "Pierwsza książka");
		book.addAuthors(author1, author2);
		if (library != null) {
			book.addLibrary(library, 1);
		} else {
			book.setLibraries(new HashSet<>());
		}
		return book;
	}

	/**
	 * If book or library is null then user has no books rent
	 */
	private UserEntity userEntityMock(BookEntity book, LibraryEntity library) {
		Set<UserBookLibraryEntity> books = new HashSet<>();
		if (book != null && library != null) {
			books.add(new UserBookLibraryEntity(userEntityMock(null, null), book, library));
		}
		return new UserEntity(BigDecimal.ONE, "user", "password", "email@domain.com", books);
	}

	@Test
	public void shouldFindUserEqualToNameVerifyPassword() {
		// given
		final String userName = "user";
		final String password = "password";
		// when
		Mockito.when(userDao.findUserEqualToNameVerifyPassword(Mockito.anyString(), Mockito.anyString()))
		    .thenReturn(this.userEntityMock(null, null));
		UserTo user = userService.findUserEqualToNameVerifyPassword(userName, password);
		// then
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(userDao).findUserEqualToNameVerifyPassword(captor.capture(), captor.capture());
		assertNotNull(user);
		assertEquals(userName, captor.getAllValues().get(0));
		assertEquals(password, captor.getAllValues().get(1));
	}

	@Test
	public void userShouldRentABook() throws BookAlreadyRentException, BookNotAvailableException {
		// given
		final LibraryEntity libraryMock = libraryEntityMock();
		final BookEntity bookMock = bookEntityMock(libraryMock);
		final UserEntity userMockAfter = userEntityMock(bookMock, libraryMock);
		final UserEntity userMockBefore = userEntityMock(null, null);

		final LibraryTo libraryArg = libraryMapper.map2To(libraryMock);
		final BookTo bookArg = bookMapper.map2To(bookMock);
		UserTo user = userMapper.map2To(userMockBefore);

		// when
		Mockito.when(userDao.rentUserABook(Mockito.any(UserEntity.class), Mockito.any(BookEntity.class),
		    Mockito.any(LibraryEntity.class))).thenReturn(userMockAfter);

		final int userBooksSizeBefore = user.getBooks().size();
		user = userService.rentUserABook(user, bookArg, libraryArg);

		// then
		ArgumentCaptor<UserEntity> captorUser = ArgumentCaptor.forClass(UserEntity.class);
		ArgumentCaptor<BookEntity> captorBook = ArgumentCaptor.forClass(BookEntity.class);
		ArgumentCaptor<LibraryEntity> captorLibrary = ArgumentCaptor.forClass(LibraryEntity.class);

		Mockito.verify(userDao).rentUserABook(captorUser.capture(), captorBook.capture(), captorLibrary.capture());
		assertEquals(userMockBefore, captorUser.getValue());
		assertEquals(bookMock, captorBook.getValue());
		assertEquals(libraryMock, captorLibrary.getValue());

		assertNotNull(user);
		assertFalse(user.getBooks().isEmpty());
		assertEquals(1, user.getBooks().size());

		final int userBooksSizeAfter = user.getBooks().size();
		assertTrue(userBooksSizeBefore >= 0);
		assertTrue(userBooksSizeAfter >= 0);
		assertEquals(userBooksSizeBefore, userBooksSizeAfter - 1);
	}

	@Test
	public void userShouldReturnABookToLibrary() throws BookNotRentException {
		// given
		final LibraryEntity libraryMock = libraryEntityMock();
		final BookEntity bookMock = bookEntityMock(libraryMock);
		final UserEntity userMockAfter = userEntityMock(null, null);
		final UserEntity userMockBefore = userEntityMock(bookMock, libraryMock);

		final LibraryTo libraryArg = libraryMapper.map2To(libraryMock);
		final BookTo bookArg = bookMapper.map2To(bookMock);
		UserTo user = userMapper.map2To(userMockBefore);

		// when
		Mockito.when(userDao.returnABookToLibrary(Mockito.any(UserEntity.class), Mockito.any(BookEntity.class),
		    Mockito.any(LibraryEntity.class))).thenReturn(userMockAfter);

		final int userBooksSizeBefore = user.getBooks().size();
		user = userService.returnABookToLibrary(user, bookArg, libraryArg);

		// then
		ArgumentCaptor<UserEntity> captorUser = ArgumentCaptor.forClass(UserEntity.class);
		ArgumentCaptor<BookEntity> captorBook = ArgumentCaptor.forClass(BookEntity.class);
		ArgumentCaptor<LibraryEntity> captorLibrary = ArgumentCaptor.forClass(LibraryEntity.class);

		Mockito.verify(userDao).returnABookToLibrary(captorUser.capture(), captorBook.capture(), captorLibrary.capture());
		assertEquals(userMockBefore, captorUser.getValue());
		assertEquals(bookMock, captorBook.getValue());
		assertEquals(libraryMock, captorLibrary.getValue());

		assertNotNull(user);
		assertTrue(user.getBooks().isEmpty());
		assertEquals(0, user.getBooks().size());

		final int userBooksSizeAfter = user.getBooks().size();
		assertTrue(userBooksSizeBefore >= 0);
		assertTrue(userBooksSizeAfter >= 0);
		assertEquals(userBooksSizeBefore, userBooksSizeAfter + 1);
	}

	/**
	 * userMockBefore.id is not set to null to verify method call (method 'equals' compares arguments so )
	 */
	@Test
	public void shouldCreateNewUser() throws UserNameExistsException, UserEmailExistsException {
		// given
		final UserEntity userMockAfter = userEntityMock(null, null);
		UserEntity userMockBefore = userMockAfter;
		userMockBefore.setId(null);
		
		UserTo userToCreate = userMapper.map2To(userMockBefore);

		// when
		Mockito.when(userDao.createNewUser(Mockito.any(UserEntity.class))).thenReturn(userMockAfter);

		userToCreate = userService.createNewUser(userToCreate);

		// then
		ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
		Mockito.verify(userDao).createNewUser(captor.capture());
		assertEquals(userMockBefore, captor.getValue());
		assertNotNull(userToCreate);
	}

}
