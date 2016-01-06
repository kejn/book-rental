package pl.edu.pwr.exception;

public class BookNotAvailableException extends Exception {

	private static final long serialVersionUID = -2565633127752576109L;

	public BookNotAvailableException(String message) {
		super(message);
	}

	public BookNotAvailableException() {
		super();
	}

}
