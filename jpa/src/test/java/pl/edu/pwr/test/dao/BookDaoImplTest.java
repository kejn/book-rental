package pl.edu.pwr.test.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.impl.BookDaoImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataAccessDaoTestConfig.class, loader=AnnotationConfigContextLoader.class)
public class BookDaoImplTest {

	@Autowired
	private BookDao bookDao;
	
	@Test
	public void testBean() {
		assertNotNull(bookDao);
		assertTrue(bookDao instanceof BookDaoImpl);
	}

}
