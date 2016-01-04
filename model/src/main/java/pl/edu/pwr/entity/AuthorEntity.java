package pl.edu.pwr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pl.edu.pwr.common.IdAware;

@Entity
@Table(name = "AUTHORS")
public class AuthorEntity implements IdAware<BigDecimal> {

	public static final String referenceAuthorIdColumnName = "AUTHOR_ID";
	protected static final String tableName = "AUTHORS";
	private static final String sequenceName = "AUTHORS_SEQ";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
	@SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1, initialValue = 1)
	private BigDecimal id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

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
