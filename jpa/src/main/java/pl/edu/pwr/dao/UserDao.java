package pl.edu.pwr.dao;

import java.math.BigDecimal;

import pl.edu.pwr.entity.UserEntity;

public interface UserDao extends Dao<UserEntity, BigDecimal> {

	/**
	 * Searches database for user (first one found) equal to given user name and
	 * verifies if given password matches the one assigned to this certain user.
	 * 
	 * @param userName
	 *            user name to match search result
	 * @param password
	 *            password to matches the one assigned to this certain user
	 * @return user entity found in database matching above criteria or
	 *         <b>null</b> otherwise.
	 */
	public UserEntity findUserEqualToNameVerifyPassword(String userName, String password);

}
