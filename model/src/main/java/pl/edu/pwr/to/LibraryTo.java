package pl.edu.pwr.to;

import java.math.BigDecimal;

import pl.edu.pwr.common.IdAware;

public class LibraryTo implements IdAware<BigDecimal> {

	private BigDecimal id;

	private String name;

	public LibraryTo() {
	}
	
	public LibraryTo(BigDecimal id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LibraryTo) {
			LibraryTo library = (LibraryTo) obj;
			return library.toString().equals(toString());
		}
		return false;
	}

	@Override
	public String toString() {
		return "@LibraryTo(id ["+ id + "], name [" + name +"])";
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
}
