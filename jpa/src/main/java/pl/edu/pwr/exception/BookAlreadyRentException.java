package pl.edu.pwr.exception;

public class BookAlreadyRentException extends Exception {

	private static final long serialVersionUID = 6896083336603491418L;

	public BookAlreadyRentException(String msg) {
		super(msg);
	}
	
	public BookAlreadyRentException() {
		super();
	}
	
}
