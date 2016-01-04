package pl.edu.pwr.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pl.edu.pwr.common.IdAware;

@Entity
@Table(name = BookEntity.tableName)
public class BookEntity implements IdAware<BigDecimal> {

	public static final String referenceBookIdColumnName = "BOOK_ID";
	public static final String referenceBookAuthorTableName = "BOOK_AUTHOR";
	public static final String referenceBookLibraryTableName = "BOOK_LIBRARY";
	protected static final String tableName = "BOOKS";
	private static final String sequenceName = "BOOKS_SEQ";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
	@SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1, initialValue = 1)
	private BigDecimal id;

	@Column(nullable = false)
	private String title;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
	    CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinTable(name = referenceBookAuthorTableName, joinColumns = {
	    @JoinColumn(name = referenceBookIdColumnName, updatable = false) }, inverseJoinColumns = {
	        @JoinColumn(name = AuthorEntity.referenceAuthorIdColumnName, updatable = false) })
	private Set<AuthorEntity> authors = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
	    CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinTable(name = "BOOK_LIBRARY", joinColumns = {
	    @JoinColumn(name = "BOOK_ID", updatable = false) }, inverseJoinColumns = {
	        @JoinColumn(name = "LIBRARY_ID", updatable = false) })
	private Set<LibraryEntity> libraries = new HashSet<>();

	protected BookEntity() {
	}

	/**
	 * Creates entity by assigning member fields.
	 * 
	 * @param id
	 *          id in database
	 * @param title
	 *          book title
	 * @param authors
	 *          set of authors who written this book
	 * @param libraries
	 *          set of libraries where this book is available
	 */
	public BookEntity(BigDecimal id, String title, Set<AuthorEntity> authors, Set<LibraryEntity> libraries) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.libraries = libraries;
	}

	/**
	 * Creates entity by assigning member fields <b>id</b> and <b>title</b>. After
	 * that it is required to call {@link #addAuthors(AuthorEntity...)} and
	 * {@link #addLibraries(LibraryEntity...)} methods.
	 * 
	 * @param id
	 *          id in database
	 * @param title
	 *          book title
	 */
	public BookEntity(BigDecimal id, String title) {
		this.id = id;
		this.title = title;
	}

	public void addAuthors(AuthorEntity... authors) {
		for (AuthorEntity author: authors) {
			this.authors.add(author);
		}
	}

	public void addLibraries(LibraryEntity... libraries) {
		for (LibraryEntity library: libraries) {
			this.libraries.add(library);
		}
	}

	@Override
	public String toString() {
		return "@BookEntity(id [" + id + "], title [" + title + "], authors ["
		    + authors.stream().map(a -> a.stringValue()).collect(Collectors.joining(", ")) + "], libraries ["
		    + libraries.stream().map(l -> l.getName()).collect(Collectors.joining(", ")) + "])";
	}

	@Override
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<AuthorEntity> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<AuthorEntity> authors) {
		this.authors = authors;
	}

	public Set<LibraryEntity> getLibraries() {
		return libraries;
	}

	public void setLibraries(Set<LibraryEntity> libraries) {
		this.libraries = libraries;
	}

}
