package pl.edu.pwr.service;

import pl.edu.pwr.to.UserTo;

/**
 * Calls various UserDao methods and converts entities to transport objects.
 * 
 * @author KNIEMCZY
 *
 */
public interface UserService {
	
	public UserTo findUserEqualToNameVerifyPassword(String userName, String password);
	
}
