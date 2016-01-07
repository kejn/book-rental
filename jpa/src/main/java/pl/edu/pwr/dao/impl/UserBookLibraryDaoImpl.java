package pl.edu.pwr.dao.impl;

import org.springframework.stereotype.Component;

import pl.edu.pwr.dao.UserBookLibraryDao;
import pl.edu.pwr.entity.QUserBookLibraryEntity;
import pl.edu.pwr.entity.UserBookLibraryEntity;
import pl.edu.pwr.entity.UserBookLibraryEntityId;

@Component
public class UserBookLibraryDaoImpl extends AbstractDao<UserBookLibraryEntity, QUserBookLibraryEntity, UserBookLibraryEntityId>
    implements UserBookLibraryDao {

	@Override
	protected void setQEntity() {
		qEntity = QUserBookLibraryEntity.userBookLibraryEntity;
	}
	
}