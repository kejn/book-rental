package pl.edu.pwr.service;

import pl.edu.pwr.to.UserTo;

public interface UserService {
	
	public UserTo findUserEqualToNameVerifyPassword(String userName, String password);
	
}
