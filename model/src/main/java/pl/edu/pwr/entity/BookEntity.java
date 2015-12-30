package pl.edu.pwr.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

@Entity
@Table(name = BookEntity.tableName)
public class BookEntity {

	protected static final String tableName = "BOOKS";
	private static final String sequenceName = "BOOKS_SEQ";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
	@SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1, initialValue = 1)
	private BigDecimal id;

	@Column
	private String title;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BOOK_AUTHOR", joinColumns = {
			@JoinColumn(name = "BOOK_ID", updatable = false, referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "AUTHOR_ID", updatable = false, referencedColumnName = "ID") })
	private Set<AuthorEntity> authors = new HashSet<>();

	protected BookEntity() {
	}

	public BookEntity(BigDecimal id, String title) {
		this.id = id;
		this.title = title;
	}

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
