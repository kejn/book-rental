package pl.edu.pwr.to;

import java.math.BigDecimal;
import java.util.Set;

import pl.edu.pwr.common.IdAware;

public class UserTo implements IdAware<BigDecimal> {

	private BigDecimal id;

	private String name;

	private String password;

	private Set<UserBookLibraryTo> books;

	public UserTo(BigDecimal id, String name, String password, Set<UserBookLibraryTo> books) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.books = books;
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

	public Set<UserBookLibraryTo> getBooks() {
		return books;
	}

	public void setBooks(Set<UserBookLibraryTo> books) {
		this.books = books;
	}

}
