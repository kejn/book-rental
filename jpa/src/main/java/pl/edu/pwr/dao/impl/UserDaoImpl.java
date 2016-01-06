package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysema.query.BooleanBuilder;

import pl.edu.pwr.dao.BookLibraryDao;
import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.entity.BookLibraryEntityId;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.entity.QUserEntity;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.exception.BookAlreadyRentException;
import pl.edu.pwr.exception.BookNotAvailableException;

@Component
public class UserDaoImpl extends AbstractDao<UserEntity, QUserEntity, BigDecimal> implements UserDao {

	@Autowired
	private BookLibraryDao bookLibraryDao;
	
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

		checkIfUserHasAlreadyRentABook(user, book);
		BookLibraryEntityId id = new BookLibraryEntityId(book, library);
		BookLibraryEntity bookLibraryEntity = bookLibraryDao.findOne(id);

		if(bookLibraryEntity.getQuantity() == 0) {
			throw new BookNotAvailableException();
		}
		bookLibraryEntity.setQuantity(bookLibraryEntity.getQuantity() - 1);
		bookLibraryDao.update(bookLibraryEntity);
		
		user.addBooks(book);
		user = update(user);
		return user;
	}

	private void checkIfUserHasAlreadyRentABook(UserEntity user, BookEntity book) throws BookAlreadyRentException {
		if(user.getBooks().contains(book)) {
			throw new BookAlreadyRentException();
		}
	}

}
