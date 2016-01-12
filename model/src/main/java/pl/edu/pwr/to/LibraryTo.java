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
