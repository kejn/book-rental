package pl.edu.pwr.service;

import pl.edu.pwr.exception.BookAlreadyRentException;
import pl.edu.pwr.exception.BookNotAvailableException;
import pl.edu.pwr.exception.BookNotRentException;
import pl.edu.pwr.exception.UserEmailExistsException;
import pl.edu.pwr.exception.UserNameExistsException;
import pl.edu.pwr.to.BookTo;
import pl.edu.pwr.to.LibraryTo;
import pl.edu.pwr.to.UserTo;

/**
 * Calls various UserDao methods and converts entities to transport objects.
 * 
 * @author KNIEMCZY
 *
 */
public interface UserService {

	public UserTo findUserEqualToNameVerifyPassword(String userName, String password);

	public UserTo rentUserABook(UserTo user, BookTo book, LibraryTo library)
	    throws BookAlreadyRentException, BookNotAvailableException;

	public UserTo returnABookToLibrary(UserTo user, BookTo book, LibraryTo library) throws BookNotRentException;

	public UserTo createNewUser(UserTo userToCreate) throws UserNameExistsException, UserEmailExistsException;

}
