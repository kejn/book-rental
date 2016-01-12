package pl.edu.pwr.dao;

import java.math.BigDecimal;

import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.exception.BookAlreadyRentException;
import pl.edu.pwr.exception.BookNotAvailableException;
import pl.edu.pwr.exception.BookNotRentException;
import pl.edu.pwr.exception.UserEmailExistsException;
import pl.edu.pwr.exception.UserNameExistsException;

public interface UserDao extends Dao<UserEntity, BigDecimal> {

	/**
	 * Searches database for user (first one found) equal to given user name and
	 * verifies if given password matches the one assigned to this certain user.
	 * 
	 * @param userName
	 *          user name to match search result
	 * @param password
	 *          password to matches the one assigned to this certain user
	 * @return user entity found in database matching above criteria or
	 *         <b>null</b> otherwise.
	 */
	public UserEntity findUserEqualToNameVerifyPassword(String userName, String password);

	/**
	 * Let given <b>user</b> rent a <b>book</b> if it is available in selected
	 * <b>library</b>.
	 * 
	 * @param user
	 *          user who wants to rent a book
	 * @param book
	 *          book which user wants to rent
	 * @param library
	 *          library where user wants to rent a book
	 * @return updated user entity (assigned a new book)
	 * 
	 * @throws BookAlreadyRentException
	 *           if <b>user</b> has already rent specified <b>book</b>
	 * @throws BookNotAvailableException
	 *           if <b>book</b> is not available in <b>library</b>
	 */
	public UserEntity rentUserABook(UserEntity user, BookEntity book, LibraryEntity library)
	    throws BookAlreadyRentException, BookNotAvailableException;

	/**
	 * Let given <b>user</b> return a <b>book</b> to library it was rent from.
	 * 
	 * @param user
	 *          user who wants to return a book
	 * @param book
	 *          book which user wants to return
	 * @return updated user entity (assigned a new book)
	 * 
	 * @throws BookNotRentException
	 *           if <b>user</b> hasn't rent specified <b>book</b> before
	 */
	public UserEntity returnABookToLibrary(UserEntity user, BookEntity book, LibraryEntity library)
	    throws BookNotRentException;

	/**
	 * Let create a new user.
	 * 
	 * @param user
	 *          user to be created
	 * @return given user with assigned <b>id</b> member field
	 * 
	 * @throws UserNameExistsException
	 *           if <b>user.name</b> already exists in database
	 * @throws UserEmailExistsException
	 *           if <b>user.email</b> already exists in database
	 */
	public UserEntity createNewUser(UserEntity user) throws UserNameExistsException, UserEmailExistsException;

	public UserEntity updateUser(UserEntity user) throws UserNameExistsException, UserEmailExistsException;

	UserEntity createNewUserWithNameLikeId(UserEntity user) throws UserNameExistsException, UserEmailExistsException;

}
