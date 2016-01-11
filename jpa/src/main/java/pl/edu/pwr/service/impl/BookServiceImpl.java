package pl.edu.pwr.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.mapper.impl.BookMapper;
import pl.edu.pwr.service.BookService;
import pl.edu.pwr.to.BookTo;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;

	@Autowired
	private BookMapper bookMapper;

	@Override
	public List<BookTo> findAll() {
		return bookMapper.map2To(bookDao.findAll());
	}
	
	@Override
	public BookTo findBookById(BigDecimal id) {
		return bookMapper.map2To(bookDao.findOne(id));
	}

	@Override
	public List<BookTo> findBooksByTitle(String bookTitle) {
		return bookMapper.map2To(bookDao.findBooksByTitle(bookTitle));
	}

	@Override
	public List<BookTo> findBooksByAuthor(String author) {
		return bookMapper.map2To(bookDao.findBooksByAuthor(author));
	}

	@Override
	public List<BookTo> findBooksByLibraryName(String libraryName) {
		return bookMapper.map2To(bookDao.findBooksByLibraryName(libraryName));
	}

	@Override
	public BookTo save(BookTo book) {
		BookEntity bookToSaveByDao = bookMapper.map2Entity(book);
		return bookMapper.map2To(bookDao.save(bookToSaveByDao));
	}

	@Override
	public List<BookTo> findBooksByTitleAuthorLibrary(String bookTitle, String bookAuthor, String bookLibrary) {
		return bookMapper.map2To(bookDao.findBooksByTitleAuthorLibrary(bookTitle,bookAuthor,bookLibrary));
	}

}
