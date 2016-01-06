package pl.edu.pwr.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import pl.edu.pwr.common.IdAware;

@Entity
@Table(name = BookLibraryEntity.tableName)
@IdClass(BookLibraryEntityId.class)
public class BookLibraryEntity implements IdAware<BookLibraryEntityId> {

	protected static final String tableName = "BOOK_LIBRARY";

	@Transient
	private BigDecimal bookId;

	@Transient
	private BigDecimal libraryId;
	
	@Column
	private int quantity;

	@Id
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@PrimaryKeyJoinColumn(name = BookEntity.referenceBookIdColumnName, referencedColumnName = "ID")
	private BookEntity book;

	@Id
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@PrimaryKeyJoinColumn(name = LibraryEntity.referenceLibraryIdColumnName, referencedColumnName = "ID")
	private LibraryEntity library;

	protected BookLibraryEntity() {
	}

	public BookLibraryEntity(BookEntity book, LibraryEntity library, int quantity){
		this.bookId = book.getId();
		this.libraryId = library.getId();
		this.book = book;
		this.library = library;
		this.quantity = quantity;
	}
	
	public boolean isBookAvailable() {
		return quantity > 0;
	}
	
	@Override
	public BookLibraryEntityId getId() {
		return new BookLibraryEntityId(book, library);
	}

	public BigDecimal getBookId() {
		return bookId;
	}

	public void setBookId(BigDecimal bookId) {
		this.book.setId(bookId);
		this.bookId = bookId;
	}

	public BigDecimal getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(BigDecimal libraryId) {
		this.book.setId(libraryId);
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
