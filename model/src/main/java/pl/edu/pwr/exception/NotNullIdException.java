package pl.edu.pwr.exception;

public class NotNullIdException extends RuntimeException {

	private static final long serialVersionUID = -833671007024938802L;

	public NotNullIdException(String msg) {
		super(msg);
	}

	public NotNullIdException() {
	}
}
