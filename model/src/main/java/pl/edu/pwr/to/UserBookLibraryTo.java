package pl.edu.pwr.to;

import java.math.BigDecimal;

public class UserBookLibraryTo {

	private BigDecimal userId;

	private BookTo book;

	private BigDecimal library;

	public UserBookLibraryTo(BigDecimal userId, BookTo book, BigDecimal library) {
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
		return library;
	}

	public void setLibraryId(BigDecimal libraryId) {
		this.library = libraryId;
	}

	public BookTo getBook() {
		return book;
	}
	
	public void setBook(BookTo book) {
		this.book = book;
	}
	
}
