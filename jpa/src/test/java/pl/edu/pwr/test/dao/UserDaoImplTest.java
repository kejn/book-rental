package pl.edu.pwr.test.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.test.config.DataAccessDaoTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessDaoTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserDaoImplTest {
	
	@Autowired
	private UserDao userDao;

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
	public void shouldfindUserEqualToNameIgnoreCaseAndVerifyPasswordCaseSensitive() {
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
	
}
