package pl.edu.pwr.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.entity.AuthorEntity;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.test.config.DataAccessDaoTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataAccessDaoTestConfig.class, loader = AnnotationConfigContextLoader.class)
public class BookDaoImplTest {

	@Autowired
	private BookDao bookDao;

	@Test
	public void shouldFindBookById() {
		// given
		final BigDecimal bookId = BigDecimal.ONE;
		// when
		BookEntity bookEntity = bookDao.findOne(bookId);
		// then
		assertNotNull(bookEntity);
	}

	@Test
	public void shouldFindBookByTitle() {
		// given
		final String titleFragment = "pierwsza";
		// then
		List<BookEntity> books = bookDao.findBooksByTitle(titleFragment);
		// then
		assertFalse(books.isEmpty());
		assertEquals("Pierwsza książka", books.get(0).getTitle());
	}

	@Test
	public void shouldFindBookByAuthorPrefixIgnoreCase() {
		// given
		final String authorPrefix = "kowalski";
		// when
		List<BookEntity> books = bookDao.findBooksByAuthor(authorPrefix);
		// then
		assertFalse(books.isEmpty());
		final Set<AuthorEntity> authors = books.get(0).getAuthors();
		assertNotNull(authors);
		assertTrue(authors.stream().anyMatch(author -> author.matchFirstOrLastName(authorPrefix)));
	}

}
