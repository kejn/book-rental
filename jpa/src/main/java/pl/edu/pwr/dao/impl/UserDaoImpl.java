package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysema.query.BooleanBuilder;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.BookLibraryDao;
import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.dao.UserBookLibraryDao;
import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.entity.BookLibraryEntityId;
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

	@Override
	public UserEntity findUserEqualToNameVerifyPassword(String userName, String password) {
		checkIfArgumentIsNull(userName, "userName");
		prepareQueryVariables();
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qEntity.name.equalsIgnoreCase(userName));
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
		if (user.getBooks().contains(userBookLibrary)) {
			throw new BookAlreadyRentException();
		}

		final BookLibraryEntityId id = new BookLibraryEntityId(book, library);
		final BookLibraryEntity bookLibraryEntity = bookLibraryDao.findOne(id);

		if (!bookLibraryEntity.isBookAvailable()) {
			throw new BookNotAvailableException();
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
		
		for(UserBookLibraryEntity ubl : user.getBooks()) {
			System.out.println("\n\nin\n" + ubl.toString() + "\n\n\n");
		}
		System.out.println("\n\nwas\n" + userBookLibrary.toString() + "\n\n\n");
		
		if(!user.removeBook(userBookLibrary)) {
			return null;
		}
		user = update(user);
		return user;
	}

	@Override
	public UserEntity createNewUser(UserEntity user) throws UserNameExistsException, UserEmailExistsException {
		checkIfArgumentIsNull(user, "user");
		prepareQueryVariables();

		BooleanBuilder builder = new BooleanBuilder();
		builder.or(qEntity.name.eq(user.getName()));
		builder.or(qEntity.email.eq(user.getEmail()));
		
		List<UserEntity> userCheck = query.from(qEntity).where(builder).list(qEntity); 

		for(UserEntity userFound : userCheck) {
			if(userFound.getName().equals(user.getName())) {
				throw new UserNameExistsException("Username (" + user.getName() + ") is already taken");
			}
			if(userFound.getEmail().equals(user.getEmail())) {
				throw new UserEmailExistsException("Username with this email (" + user.getEmail() + ") already exists");
			}
		}

		return save(user);
	}

}
