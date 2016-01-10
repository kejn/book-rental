package pl.edu.pwr.exception;

public class UserEmailExistsException extends Exception {

	private static final long serialVersionUID = 2115622530112280535L;

	public UserEmailExistsException(){
		super();
	}
	
	public UserEmailExistsException(String msg) {
		super(msg);
	}
}
