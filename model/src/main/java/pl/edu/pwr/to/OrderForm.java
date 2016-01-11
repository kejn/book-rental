package pl.edu.pwr.to;

public class OrderForm {

	private UserTo user;
	
	private BookTo book;
	
	private LibraryTo library;
	
	public OrderForm(UserTo user, BookTo book, LibraryTo library) {
		this.user = user;
		this.book = book;
		this.library = library;
	}

	public UserTo getUser() {
		return user;
	}

	public void setUser(UserTo user) {
		this.user = user;
	}

	public BookTo getBook() {
		return book;
	}

	public void setBook(BookTo book) {
		this.book = book;
	}

	public LibraryTo getLibrary() {
		return library;
	}

	public void setLibrary(LibraryTo library) {
		this.library = library;
	}
}
