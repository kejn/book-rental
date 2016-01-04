package pl.edu.pwr.to;

import java.math.BigDecimal;

import pl.edu.pwr.common.IdAware;

public class AuthorTo implements IdAware<BigDecimal> {

	private BigDecimal id;

	private String firstName;

	private String lastName;

	public AuthorTo(BigDecimal id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
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
