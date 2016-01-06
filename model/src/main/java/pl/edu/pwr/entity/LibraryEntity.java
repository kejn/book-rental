package pl.edu.pwr.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = LibraryEntity.tableName)
public class LibraryEntity implements IdAware<BigDecimal> {

	public static final String referenceLibraryIdColumnName = "LIBRARY_ID";
	protected static final String tableName = "LIBRARIES";
	private static final String sequenceName = "LIBRARIES_SEQ";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = sequenceName)
	@SequenceGenerator(name = sequenceName, sequenceName = sequenceName, allocationSize = 1, initialValue = 1)
	private BigDecimal id;

	@Column(nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "library")
	private List<BookLibraryEntity> books;

	protected LibraryEntity() {
	}

	public LibraryEntity(BigDecimal id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LibraryEntity) {
			LibraryEntity library = (LibraryEntity) obj;
			return library.getId().equals(id) && library.getName().equals(name);
		}
		return false;
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
