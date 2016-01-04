package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.edu.pwr.dao.LibraryDao;
import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.entity.QLibraryEntity;

@Component
public class LibraryDaoImpl extends AbstractDao<LibraryEntity, QLibraryEntity, BigDecimal> implements LibraryDao {
	
	@Override
	protected void setQEntity() {
		qEntity = QLibraryEntity.libraryEntity;
	}

	@Override
	public List<LibraryEntity> findLibrariesByName(String name) {
		checkIfArgumentIsNull(name, "name");
		prepareQueryVariables();
		return query.from(qEntity).where(qEntity.name.containsIgnoreCase(name)).list(qEntity);
	}
}
