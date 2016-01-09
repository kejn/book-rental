package pl.edu.pwr.to;

import java.math.BigDecimal;

public class UserBookLibraryTo {

	private BigDecimal userId;

	private BookTo book;

	private LibraryTo library;

	public UserBookLibraryTo(BigDecimal userId, BookTo book, LibraryTo library) {
		this.userId = userId;
		this.book = book;
		this.library = library;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public BigDecimal getBookId() {
		return book.getId();
	}

	public void setBookId(BigDecimal bookId) {
		this.book.setId(bookId);
	}

	public BigDecimal getLibraryId() {
		return library.getId();
	}

	public void setLibraryId(BigDecimal libraryId) {
		this.library.setId(libraryId);
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
