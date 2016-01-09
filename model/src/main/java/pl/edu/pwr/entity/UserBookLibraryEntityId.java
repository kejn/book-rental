package pl.edu.pwr.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserBookLibraryEntityId implements Serializable {

	private static final long serialVersionUID = -8770503844828295956L;

	private UserEntity user;

	private BookEntity book;

	private LibraryEntity library;

	protected UserBookLibraryEntityId() {
	}

	public UserBookLibraryEntityId(UserEntity userId, BookEntity bookId, LibraryEntity library) {
		this.user = userId;
		this.book = bookId;
		this.library = library;
	}

	public UserBookLibraryEntityId(BigDecimal userId, BigDecimal bookId, BigDecimal libraryId) {
		this.user = new UserEntity(userId);
		this.book = new BookEntity(bookId);
		this.library = new LibraryEntity(libraryId);
	}

	@Override
	public int hashCode() {
		return user.hashCode() + book.hashCode() + library.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserBookLibraryEntityId) {
			UserBookLibraryEntityId id = (UserBookLibraryEntityId) obj;
			return id.user.getId().equals(user.getId()) && id.book.getId().equals(book.getId())
			    && id.library.equals(library);
		}
		return false;
	}

	@Override
	public String toString() {
		return "user: " + user.getId() + ", book: " + book.getId() + ", library: " + library;
	}
	
	public UserEntity getUser() {
		return user;
	}
	
	public void setUser(UserEntity user) {
		this.user = user;
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
