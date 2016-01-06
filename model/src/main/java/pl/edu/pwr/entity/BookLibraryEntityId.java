package pl.edu.pwr.entity;

import java.io.Serializable;

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

	@Override
	public int hashCode() {
		return book.getId().intValue() + library.getId().intValue();
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
