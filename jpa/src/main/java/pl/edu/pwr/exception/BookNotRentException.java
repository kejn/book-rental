package pl.edu.pwr.exception;

public class BookNotRentException extends Exception {

	private static final long serialVersionUID = 6896083336603491418L;

	public BookNotRentException(String msg) {
		super(msg);
	}
	
	public BookNotRentException() {
		super();
	}
	
}
