package pl.edu.pwr.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pl.edu.pwr.common.IdAware;

@Entity
@Table(name = UserBookLibraryEntity.tableName)
@IdClass(UserBookLibraryEntityId.class)
public class UserBookLibraryEntity implements IdAware<UserBookLibraryEntityId> {

	protected static final String tableName = "USER_BOOK_LIBRARY";

	@Id
	@ManyToOne
	@JoinColumn(name = UserEntity.referenceUserIdColumnName, referencedColumnName = "ID", nullable = false, unique = false)
	private UserEntity user;

	@Id
	@ManyToOne
	@JoinColumn(name = BookEntity.referenceBookIdColumnName, referencedColumnName = "ID", nullable = false, unique = false)
	private BookEntity book;

	@Id
	@ManyToOne
	@JoinColumn(name = LibraryEntity.referenceLibraryIdColumnName, nullable = false, unique = false, updatable = false)
	private LibraryEntity library;

	protected UserBookLibraryEntity() {
	}

	public UserBookLibraryEntity(UserEntity user, BookEntity book, LibraryEntity library) {
		this.user = user;
		this.book = book;
		this.library = library;
	}

	@Override
	public String toString() {
		return "user: " + user.getId() + ", book: " + book.getId() + ", library: " + library.getId();
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserBookLibraryEntity) {
			UserBookLibraryEntity bookLibrary = (UserBookLibraryEntity) obj;
			return bookLibrary.toString().equals(toString());
		}
		return false;
	}

	@Override
	public UserBookLibraryEntityId getId() {
		return new UserBookLibraryEntityId(user, book, library);
	}

	public BigDecimal getUserId() {
		return user.getId();
	}

	public void setUserId(BigDecimal userId) {
		this.user.setId(userId);
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
