package pl.edu.pwr.test.properties;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.edu.pwr.config.DatabaseConfig;
import pl.edu.pwr.test.config.DataAccessTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class PropertiesTest {

	@Autowired
	private DatabaseConfig databaseConfig;

	@Test
	public void shouldCheckOracleProperties() {
		// given
		final String user = "BOOK_RENTAL_TEST";
		final String password = "Projects2015";
		final String url = "jdbc:oracle:thin:@//localhost:1521/xe";
		// then
		assertEquals(user, databaseConfig.getUser());
		assertEquals(password, databaseConfig.getPassword());
		assertEquals(url, databaseConfig.getUrl());
	}

}
