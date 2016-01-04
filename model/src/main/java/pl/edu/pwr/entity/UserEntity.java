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
@Table(name = UserEntity.tableName)
public class UserEntity {

	public static final String referenceUserIdColumnName = "USER_ID";
	public static final String referenceUserBookTableName = "USER_BOOK";
	protected static final String tableName = "USERS";
	private static final String sequenceName = "USERS_SEQ";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
	@SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1, initialValue = 1)
	private BigDecimal id;
	
	@Column(unique=true, nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinTable(name = referenceUserBookTableName, joinColumns = {
			@JoinColumn(name = referenceUserIdColumnName, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = BookEntity.referenceBookIdColumnName, updatable = false) })
	private Set<BookEntity> books = new HashSet<>();
	
	protected UserEntity() {
	}

	public UserEntity(BigDecimal id, String name, String password, Set<BookEntity> books) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.books = books;
	}
	
	public UserEntity(BigDecimal id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
	
	public void addBooks(BookEntity... books) {
		for(BookEntity book : books) {
			this.books.add(book);
		}
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(Set<BookEntity> books) {
		this.books = books;
	}
}
