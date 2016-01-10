package pl.edu.pwr.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pl.edu.pwr.common.IdAware;

@Entity
@Table(name = UserEntity.tableName)
public class UserEntity implements IdAware<BigDecimal> {

	public static final String referenceUserIdColumnName = "USER_ID";
	protected static final String tableName = "USERS";
	private static final String sequenceName = "USERS_SEQ";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
	@SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1, initialValue = 1)
	private BigDecimal id;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String email;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<UserBookLibraryEntity> books = new HashSet<>();

	protected UserEntity() {
	}

	public UserEntity(BigDecimal id, String name, String password, String email, Set<UserBookLibraryEntity> books) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.books = books;
	}

	/**
	 * For UserBookLibrary
	 */
	public UserEntity(BigDecimal userId) {
		this.id = userId;
	}

	public void addBook(BookEntity book, LibraryEntity library) {
		this.books.add(new UserBookLibraryEntity(this, book, library));
	}

	public boolean removeBook(UserBookLibraryEntity userBookLibrary) {
		Iterator<UserBookLibraryEntity> iterator = this.books.iterator();
		boolean removed = false;
		while (iterator.hasNext()) {
			if (iterator.next().equals(userBookLibrary)) {
				iterator.remove();
				removed = true;
				break;
			}
		}
		return removed;
	}

	public UserBookLibraryEntity getBook(BookEntity book) {
		for (UserBookLibraryEntity userBookLibrary: books) {
			if (userBookLibrary.getBook().getId().equals(book.getId())) {
				return userBookLibrary;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserEntity) {
			UserEntity user = (UserEntity) obj;
			return user.toString().equals(toString());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "@UserEntity(id [" + id + "], name [" + name + "], password [" + password + "], email [" + email + "], books ["
		    + books.stream().map(b -> b.toString()).collect(Collectors.joining(", ")) + "])";
	}

	@Override
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public Set<UserBookLibraryEntity> getBooks() {
		return books;
	}

	public void setBooks(Set<UserBookLibraryEntity> books) {
		this.books = books;
	}

}
