package pl.edu.pwr.test.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.mapper.impl.AuthorMapper;
import pl.edu.pwr.mapper.impl.BookMapper;
import pl.edu.pwr.mapper.impl.UserMapper;
import pl.edu.pwr.mapper.impl.BookLibraryMapper;
import pl.edu.pwr.service.impl.UserServiceImpl;
import pl.edu.pwr.to.UserTo;

public class UserServiceImplTest {

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserServiceImpl userService;

	private UserMapper userMapper = new UserMapper(new BookMapper(new AuthorMapper(), new BookLibraryMapper()));

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Whitebox.setInternalState(userService, "userMapper", userMapper);
	}

	@Test
	public void shouldFindUserEqualToNameVerifyPassword() {
		// given
		final BigDecimal id = BigDecimal.ONE;
		final String userName = "user";
		final String password = "password";
		// when
		Mockito.when(userDao.findUserEqualToNameVerifyPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(new UserEntity(id, userName, password));
		UserTo user = userService.findUserEqualToNameVerifyPassword(userName, password);
		// then
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(userDao).findUserEqualToNameVerifyPassword(captor.capture(), captor.capture());
		assertNotNull(user);
		assertEquals(userName, captor.getAllValues().get(0));
		assertEquals(password, captor.getAllValues().get(1));
	}

}
