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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pl.edu.pwr.common.IdAware;

@Entity
@Table(name = "AUTHORS")
public class AuthorEntity implements IdAware<BigDecimal> {

	protected static final String tableName = "AUTHORS";
	private static final String sequenceName = "AUTHORS_SEQ";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
	@SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1, initialValue = 1)
	private BigDecimal id;

	@Column
	private String firstName;

	@Column
	private String lastName;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "BOOK_AUTHOR", joinColumns = {
			@JoinColumn(name = "AUTHOR_ID", updatable = false, referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "BOOK_ID", updatable = false, referencedColumnName = "ID") })
	Set<BookEntity> books = new HashSet<>();

	protected AuthorEntity() {
	}

	public AuthorEntity(BigDecimal id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public boolean matchFirstOrLastName(String name) {
		String lowerCaseName = name.toLowerCase();
		return firstName.toLowerCase().startsWith(lowerCaseName) || lastName.toLowerCase().startsWith(lowerCaseName);
	}

	@Override
	public String toString() {
		return "@AuthorEntity(id [" + id + "], firstName [" + firstName + "], lastName [" + lastName + "]";
	}
	
	public String stringValue() {
		return firstName + " " + lastName;
	}

	@Override
	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
