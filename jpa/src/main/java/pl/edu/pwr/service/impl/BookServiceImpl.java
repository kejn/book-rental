package pl.edu.pwr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.mapper.impl.BookMapper;
import pl.edu.pwr.service.BookService;
import pl.edu.pwr.to.BookTo;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private BookMapper bookMapper;
	
	public List<BookTo> findBooksByTitle(String bookTitle) {
		return (List<BookTo>) bookMapper.map2To(bookDao.findBooksByTitle(bookTitle));
	}

}
