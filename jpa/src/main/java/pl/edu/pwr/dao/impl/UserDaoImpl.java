package pl.edu.pwr.dao.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.mysema.query.BooleanBuilder;

import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.entity.QUserEntity;
import pl.edu.pwr.entity.UserEntity;

@Component
public class UserDaoImpl extends AbstractDao<UserEntity, QUserEntity, BigDecimal> implements UserDao {

	@Override
	protected void prepareQueryVariables() {
		createJPAQuery();
		qEntity = QUserEntity.userEntity;
	}

	@Override
	public UserEntity findUserEqualToNameVerifyPassword(String userName, String password) {
		checkIfArgumentIsNull(userName, "userName");
		prepareQueryVariables();
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qEntity.name.equalsIgnoreCase(userName));
		builder.and(qEntity.password.eq(password));
		return query.from(qEntity).where(builder).singleResult(qEntity);
	}


}
