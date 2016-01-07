package pl.edu.pwr.to;

import java.math.BigDecimal;

public class BookLibraryTo {

	private BigDecimal bookId;

	private LibraryTo library;

	private int quantity;

	public BookLibraryTo(BigDecimal bookId, LibraryTo library, int quantity) {
		this.bookId = bookId;
		this.library = library;
		this.quantity = quantity;
	}

	public BigDecimal getBookId() {
		return bookId;
	}

	public void setBookId(BigDecimal bookId) {
		this.bookId = bookId;
	}

	public BigDecimal getLibraryId() {
		return library.getId();
	}

	public void setLibraryId(BigDecimal libraryId) {
		this.library.setId(libraryId);
	}

	public LibraryTo getLibrary() {
		return library;
	}

	public void setLibrary(LibraryTo library) {
		this.library = library;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
