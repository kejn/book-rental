package pl.edu.pwr.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class UserBookLibraryEntityId implements Serializable {

	private static final long serialVersionUID = -8770503844828295956L;

	private UserEntity user;

	private BookEntity book;

	private BigDecimal libraryId;

	protected UserBookLibraryEntityId() {
	}

	public UserBookLibraryEntityId(UserEntity userId, BookEntity bookId, BigDecimal library) {
		this.user = userId;
		this.book = bookId;
		this.libraryId = library;
	}

	public UserBookLibraryEntityId(BigDecimal userId, BigDecimal bookId, BigDecimal libraryId) {
		this.user = new UserEntity(userId);
		this.book = new BookEntity(bookId);
		this.libraryId = libraryId;
	}

	@Override
	public int hashCode() {
		return user.hashCode() + book.hashCode() + libraryId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserBookLibraryEntityId) {
			UserBookLibraryEntityId id = (UserBookLibraryEntityId) obj;
			return id.user.getId().equals(user.getId()) && id.book.getId().equals(book.getId())
			    && id.libraryId.equals(libraryId);
		}
		return false;
	}

	@Override
	public String toString() {
		return "user: " + user.getId() + ", book: " + book.getId() + ", library: " + libraryId;
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

	public BigDecimal getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(BigDecimal library) {
		this.libraryId = library;
	}

}
