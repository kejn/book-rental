package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.BookLibraryDao;
import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.dao.UserBookLibraryDao;
import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.entity.QUserEntity;
import pl.edu.pwr.entity.UserBookLibraryEntity;
import pl.edu.pwr.entity.UserBookLibraryEntityId;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.exception.BookAlreadyRentException;
import pl.edu.pwr.exception.BookNotAvailableException;
import pl.edu.pwr.exception.BookNotRentException;
import pl.edu.pwr.exception.UserEmailExistsException;
import pl.edu.pwr.exception.UserNameExistsException;
import pl.edu.pwr.tool.StringCheck;

@Component
public class UserDaoImpl extends AbstractDao<UserEntity, QUserEntity, BigDecimal> implements UserDao {

	@Autowired
	private BookDao bookDao;

	@Autowired
	private LibraryDao libraryDao;

	@Autowired
	private BookLibraryDao bookLibraryDao;
	
	@Autowired
	private UserBookLibraryDao userBookLibraryDao;

	@Override
	protected void setQEntity() {
		qEntity = QUserEntity.userEntity;
	}

	private Predicate conditionNameEqualsIgnoreCase(String userName) {
		return qEntity.name.equalsIgnoreCase(userName);
	}

	private Predicate conditionEmailEquals(String email) {
		return qEntity.email.eq(email);
	}
	
	@Override
	public UserEntity findUserEqualToNameVerifyPassword(String userName, String password) {
		checkIfArgumentIsNull(userName, "userName");
		prepareQueryVariables();
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(conditionNameEqualsIgnoreCase(userName));
		builder.and(qEntity.password.eq(password));
		return query.from(qEntity).where(builder).singleResult(qEntity);
	}

	@Override
	public UserEntity rentUserABook(UserEntity user, BookEntity book, LibraryEntity library)
	    throws BookNotAvailableException, BookAlreadyRentException {
		checkIfArgumentIsNull(user, "user");
		checkIfArgumentIsNull(book, "book");
		checkIfArgumentIsNull(library, "library");

		final UserBookLibraryEntity userBookLibrary = new UserBookLibraryEntity(user, book, library);
		if (user.getBooks().contains(userBookLibrary))  {
			throw new BookAlreadyRentException();
		}

		bookLibraryDao.removeBookFromLibrary(book, library);
		
		book = bookDao.update(book);
		library = libraryDao.update(library);
		user = update(user);
		
		userBookLibraryDao.save(new UserBookLibraryEntity(user, book, library));
		user.addBook(book, library);
		user = update(user);
		return user;
	}
	
	@Override
	public UserEntity returnABookToLibrary(UserEntity user, BookEntity book, LibraryEntity library) throws BookNotRentException {
		checkIfArgumentIsNull(user, "user");
		checkIfArgumentIsNull(book, "book");
		checkIfArgumentIsNull(library, "library");

		UserBookLibraryEntityId id = new UserBookLibraryEntityId(user, book, library);
		UserBookLibraryEntity userBookLibrary = userBookLibraryDao.findOne(id);
		
		if (userBookLibrary == null) {
			throw new BookNotRentException();
		}
				
		bookLibraryDao.addBookToLibrary(book, library);
		userBookLibraryDao.delete(userBookLibrary);
		
		user.removeBook(userBookLibrary);
		user = update(user);
		return user;
	}

	private void checkUserBeforeSave(UserEntity user) throws UserNameExistsException, UserEmailExistsException {
		prepareQueryVariables();
		
		BooleanBuilder builder = new BooleanBuilder();
		builder.or(conditionNameEqualsIgnoreCase(user.getName()));
		builder.or(conditionEmailEquals(user.getEmail()));
		
		List<UserEntity> userCheck = query.from(qEntity).where(builder).list(qEntity); 
		
		for(UserEntity userFound : userCheck) {
			if(userFound.getName().equals(user.getName())) {
				throw new UserNameExistsException("Username (" + user.getName() + ") is already taken");
			}
			if(userFound.getEmail().equals(user.getEmail())) {
				throw new UserEmailExistsException("Username with this email (" + user.getEmail() + ") already exists");
			}
		}
	}
	
	@Override
	public UserEntity createNewUser(UserEntity user) throws UserNameExistsException, UserEmailExistsException {
		checkIfArgumentIsNull(user, "user");
		checkUserBeforeSave(user);
		return save(user);
	}

	@Override
	public UserEntity findUserEqualToEmail(String email) {
		checkIfArgumentIsNull(email, "email");
		prepareQueryVariables();
		return query.from(qEntity).where(conditionEmailEquals(email)).singleResult(qEntity);
	}

	@Override
	public UserEntity findUserEqualToEmailOrName(String email, String name) {
		prepareQueryVariables();

		BooleanBuilder builder = new BooleanBuilder();
		if(!StringCheck.stringIsNullOrEmpty(email)) {
			builder.or(qEntity.email.eq(email));
		}
		if(!StringCheck.stringIsNullOrEmpty(name)) {
			builder.or(qEntity.name.eq(name));
		}
		return query.from(qEntity).where(builder).singleResult(qEntity);
	}

}
