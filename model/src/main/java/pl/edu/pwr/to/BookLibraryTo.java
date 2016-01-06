package pl.edu.pwr.to;

import java.math.BigDecimal;

import pl.edu.pwr.entity.BookEntity;
import pl.edu.pwr.entity.LibraryEntity;

public class BookLibraryTo {

	private BigDecimal bookId;

	private BigDecimal libraryId;

	private int quantity;

	private BookEntity book;

	private LibraryEntity library;

	public BookLibraryTo(BookEntity book, LibraryEntity library, int quantity) {
		this.bookId = book.getId();
		this.libraryId = library.getId();
		this.quantity = quantity;
		this.book = book;
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

	public BookEntity getBook() {
		return book;
	}

	public void setBook(BookEntity book) {
		this.book = book;
	}

	public LibraryEntity getLibrary() {
		return library;
	}

	public void setLibrary(LibraryEntity library) {
		this.library = library;
	}

}
