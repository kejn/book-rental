package pl.edu.pwr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.exception.BookAlreadyRentException;
import pl.edu.pwr.exception.BookNotAvailableException;
import pl.edu.pwr.exception.BookNotRentException;
import pl.edu.pwr.exception.UserEmailExistsException;
import pl.edu.pwr.exception.UserNameExistsException;
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
		UserEntity user = userDao.findUserEqualToNameVerifyPassword(userName, password);
		return (user == null) ? null : userMapper.map2To(user);
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
	public UserTo returnABookToLibrary(UserTo user, BookTo book, LibraryTo library) throws BookNotRentException {
		UserEntity userEntity = userMapper.map2Entity(user);
		BookEntity bookEntity = bookMapper.map2Entity(book);
		LibraryEntity libraryEntity = libraryMapper.map2Entity(library);
		return userMapper.map2To(userDao.returnABookToLibrary(userEntity, bookEntity, libraryEntity));
	}

	public UserTo createNewUser(UserTo userToCreate) throws UserNameExistsException, UserEmailExistsException {
		return userMapper.map2To(userDao.createNewUser(userMapper.map2Entity(userToCreate)));
	}

	@Override
	public UserTo updateUser(UserTo userToUpdate) {
		return userMapper.map2To(userDao.updateUser(userMapper.map2Entity(userToUpdate)));
	}

	@Override
	public UserTo findUserEqualToEmail(String email) {
		UserEntity user = userDao.findUserEqualToEmail(email);
		return (user == null) ? null : userMapper.map2To(user);
	}

	@Override
	public UserTo findUserEqualToEmailOrName(String email, String name) {
		UserEntity user = userDao.findUserEqualToEmailOrName(email, name);
		return (user == null) ? null : userMapper.map2To(user);
	}

}
