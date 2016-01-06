package pl.edu.pwr.to;

import java.math.BigDecimal;

public class BookLibraryTo {

	private BigDecimal bookId;

	private BigDecimal libraryId;

	private int quantity;

	private BookTo book;

	private LibraryTo library;

	public BookLibraryTo(BookTo bookTo, LibraryTo library, int quantity) {
		this.bookId = bookTo.getId();
		this.libraryId = library.getId();
		this.quantity = quantity;
		this.book = bookTo;
		this.library = library;
	}

	public BigDecimal getBookId() {
		return bookId;
	}

	public void setBookId(BigDecimal bookId) {
		this.bookId = bookId;
	}

	public BigDecimal getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(BigDecimal libraryId) {
		this.libraryId = libraryId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
