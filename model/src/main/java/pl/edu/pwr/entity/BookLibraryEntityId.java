package pl.edu.pwr.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookLibraryEntityId implements Serializable {
	
	private static final long serialVersionUID = -7027058487214148749L;

	private BookEntity book;
	
	private LibraryEntity library;

	protected BookLibraryEntityId() {
	}
	
	public BookLibraryEntityId(BookEntity bookId, LibraryEntity libraryId) {
		this.book = bookId;
		this.library = libraryId;
	}

	public BookLibraryEntityId(BigDecimal bookId, BigDecimal libraryId) {
		this.book = new BookEntity(bookId);
		this.library = new LibraryEntity(libraryId);
	}

	@Override
	public int hashCode() {
		return book.hashCode() + library.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BookLibraryEntityId) {
			BookLibraryEntityId id = (BookLibraryEntityId) obj;
			return id.book.getId().equals(book.getId()) && id.library.getId().equals(library.getId());
		}
		return false;
	}

	public BookEntity getBook() {
		return book;
	}

	public void setBookId(BookEntity book) {
		this.book = book;
	}

	public LibraryEntity getLibrary() {
		return library;
	}

	public void setLibraryId(LibraryEntity library) {
		this.library = library;
	}

}
