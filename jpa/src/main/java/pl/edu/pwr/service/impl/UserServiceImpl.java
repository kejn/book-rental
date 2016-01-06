package pl.edu.pwr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pwr.dao.UserDao;
import pl.edu.pwr.mapper.impl.UserMapper;
import pl.edu.pwr.service.UserService;
import pl.edu.pwr.to.UserTo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserTo findUserEqualToNameVerifyPassword(String userName, String password) {
		return userMapper.map2To(userDao.findUserEqualToNameVerifyPassword(userName, password));
	}

}
