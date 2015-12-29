package pl.edu.pwr.test.dao;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.test.config.DataAccessDaoTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataAccessDaoTestConfig.class, loader=AnnotationConfigContextLoader.class)
public class BookDaoImplTest {

	@Autowired
	private BookDao bookDao;
	
	@Test
	public void testBean() {
		assertNotNull(bookDao);
	}

	@Test
	public void shouldFindBookById() {
		// given
		final BigDecimal bookId = BigDecimal.ONE;
		// when
		BookEntity bookEntity = bookDao.findOne(bookId);
		// then
		assertNotNull(bookEntity);
	}
}
