package pl.edu.pwr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.exception.BookAlreadyRentException;
import pl.edu.pwr.exception.BookNotAvailableException;
import pl.edu.pwr.mapper.impl.BookMapper;
import pl.edu.pwr.mapper.impl.LibraryMapper;
import pl.edu.pwr.mapper.impl.UserMapper;
import pl.edu.pwr.service.UserService;
import pl.edu.pwr.to.BookTo;
import pl.edu.pwr.to.LibraryTo;
import pl.edu.pwr.to.UserTo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private LibraryMapper libraryMapper;

	@Override
	public UserTo findUserEqualToNameVerifyPassword(String userName, String password) {
		return userMapper.map2To(userDao.findUserEqualToNameVerifyPassword(userName, password));
	}

	@Override
	public UserTo rentUserABook(UserTo user, BookTo book, LibraryTo library)
	    throws BookAlreadyRentException, BookNotAvailableException {
		UserEntity userEntity = userMapper.map2Entity(user);
		BookEntity bookEntity = bookMapper.map2Entity(book);
		LibraryEntity libraryEntity = libraryMapper.map2Entity(library);
		return userMapper.map2To(userDao.rentUserABook(userEntity, bookEntity, libraryEntity));
	}

	@Override
	public UserTo returnABookToLibrary(UserTo user, BookTo book, LibraryTo library) {
		// TODO Auto-generated method stub
		return null;
	}

}
