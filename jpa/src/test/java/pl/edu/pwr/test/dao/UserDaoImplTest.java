package pl.edu.pwr.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.test.config.DataAccessTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserDaoImplTest {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImplTest.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private BookDao bookDao;

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
	 * !!! depends on {@link #shouldFindUserById()} and
	 * {@link pl.edu.pwr.test.dao.BookDaoImplTest#shouldFindBookById()}
	 */
	@Test
	public void shouldUpdateUserWithNewBook() {
		// given
		final BookEntity book = bookDao.findOne(new BigDecimal("1"));
		logger.info(book.toString());
		// when
		UserEntity user = userDao.findOne(new BigDecimal("2"));
		user.addBooks(book);
		user = userDao.update(user);
		// then
		assertNotNull(user);
		assertFalse(user.getBooks().isEmpty());
		assertEquals(1, user.getBooks().size());
	}

}
