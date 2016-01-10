package pl.edu.pwr.exception;

public class UserNameExistsException extends Exception {

	private static final long serialVersionUID = -3210384664656804902L;

	public UserNameExistsException(){
		super();
	}
	
	public UserNameExistsException(String msg) {
		super(msg);
	}
}
