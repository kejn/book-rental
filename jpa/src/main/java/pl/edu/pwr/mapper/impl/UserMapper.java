package pl.edu.pwr.mapper.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.mapper.Mapper;
import pl.edu.pwr.to.UserTo;

@Component
public class UserMapper implements Mapper<UserTo, UserEntity> {

	@Autowired
	private UserBookLibraryMapper userBookLibraryMapper;

	protected UserMapper() {
	}
	
	public UserMapper(UserBookLibraryMapper userBookLibraryMapper) {
		this.userBookLibraryMapper = userBookLibraryMapper;
	}
	
	@Override
	public UserTo map2To(UserEntity entity) {
		entity.getId();
		entity.getName();
		entity.getName();
		entity.getBooks();
		return new UserTo(entity.getId(), entity.getName(), entity.getPassword(),
		    userBookLibraryMapper.map2To(entity.getBooks()).stream().collect(Collectors.toSet()));
	}

	@Override
	public UserEntity map2Entity(UserTo to) {
		return new UserEntity(to.getId(), to.getName(), to.getPassword(),
		    userBookLibraryMapper.map2Entity(to.getBooks()).stream().collect(Collectors.toSet()));
	}

	@Override
	public List<UserTo> map2To(Collection<UserEntity> entities) {
		return entities.stream().map(this::map2To).collect(Collectors.toList());
	}

	@Override
	public List<UserEntity> map2Entity(Collection<UserTo> tos) {
		return tos.stream().map(this::map2Entity).collect(Collectors.toList());
	}

}
