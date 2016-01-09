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

	protected LibraryEntity() {
	}

	/**
	 * Creates entity by assigning member fields <b>id</b> and <b>name</b>.

	 * @param id
	 *          id in database
	 * @param name
	 *          library name
	 */
	public LibraryEntity(BigDecimal id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * For BookLibraryMapper
	 */
	public LibraryEntity(BigDecimal libraryId) {
		this.id = libraryId;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
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
