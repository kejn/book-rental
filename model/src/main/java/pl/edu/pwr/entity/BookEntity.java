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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pl.edu.pwr.common.IdAware;

@Entity
@Table(name = BookEntity.tableName)
public class BookEntity implements IdAware<BigDecimal> {

	protected static final String tableName = "BOOKS";
	private static final String sequenceName = "BOOKS_SEQ";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
	@SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1, initialValue = 1)
	private BigDecimal id;

	@Column
	private String title;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BOOK_AUTHOR", joinColumns = {
			@JoinColumn(name = "BOOK_ID", updatable = false, referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "AUTHOR_ID", updatable = false, referencedColumnName = "ID") })
	private Set<AuthorEntity> authors;

	protected BookEntity() {
	}

	/**
	 * Creates entity by assigning member fields.
	 * 
	 * @param id
	 *            id in database
	 * @param title
	 *            book title
	 * @param authors
	 *            set of authors who written this book
	 */
	public BookEntity(BigDecimal id, String title, Set<AuthorEntity> authors) {
		this.id = id;
		this.title = title;
		this.authors = authors;
	}

	/**
	 * Creates entity by assigning member fields.
	 * 
	 * @param id
	 *            id in database
	 * @param title
	 *            book title
	 * @param authors
	 *            authors of AuthorEntity type separated by commas
	 */
	public BookEntity(BigDecimal id, String title, AuthorEntity... authors) {
		this.id = id;
		this.title = title;
		this.authors = new HashSet<>();
		for (AuthorEntity author : authors) {
			this.authors.add(author);
		}
	}

	@Override
	public String toString() {
		return "@BookEntity(id [" + id + "], title [" + title + "], authors ["
				+ authors.stream().map(a -> a.stringValue()).collect(Collectors.joining(", ")) + "])";
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

}
