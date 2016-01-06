package pl.edu.pwr.to;

import java.math.BigDecimal;
import java.util.Set;

import pl.edu.pwr.common.IdAware;

public class BookTo implements IdAware<BigDecimal> {

	private BigDecimal id;

	private String title;

	private Set<AuthorTo> authors;

	private Set<BookLibraryTo> libraries;

	public BookTo(BigDecimal id, String title, Set<AuthorTo> authors, Set<BookLibraryTo> libraries) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.libraries = libraries;
	}

	public BookTo(BigDecimal bookId) {
		this.id = bookId;
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

	public Set<AuthorTo> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<AuthorTo> authors) {
		this.authors = authors;
	}

	public Set<BookLibraryTo> getLibraries() {
		return libraries;
	}

	public void setLibraries(Set<BookLibraryTo> libraries) {
		this.libraries = libraries;
	}
}
