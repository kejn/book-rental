package pl.edu.pwr.mapper.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.pwr.entity.UserBookLibraryEntity;
import pl.edu.pwr.entity.UserEntity;
import pl.edu.pwr.mapper.Mapper;
import pl.edu.pwr.to.UserBookLibraryTo;

@Component
public class UserBookLibraryMapper implements Mapper<UserBookLibraryTo, UserBookLibraryEntity> {

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private LibraryMapper libraryMapper;

	protected UserBookLibraryMapper() {
	}

	public UserBookLibraryMapper(BookMapper bookMapper, LibraryMapper libraryMapper) {
		this.bookMapper = bookMapper;
		this.libraryMapper = libraryMapper;
	}

	@Override
	public UserBookLibraryTo map2To(UserBookLibraryEntity entity) {
		return new UserBookLibraryTo(entity.getUserId(), bookMapper.map2To(entity.getBook()),
		    libraryMapper.map2To(entity.getLibrary()));
	}

	@Override
	public UserBookLibraryEntity map2Entity(UserBookLibraryTo to) {
		return new UserBookLibraryEntity(new UserEntity(to.getUserId()), bookMapper.map2Entity(to.getBook()),
				libraryMapper.map2Entity(to.getLibrary()));
	}

	@Override
	public Set<UserBookLibraryTo> map2To(Collection<UserBookLibraryEntity> entities) {
		return entities.stream().map(this::map2To).collect(Collectors.toSet());
	}

	@Override
	public Set<UserBookLibraryEntity> map2Entity(Collection<UserBookLibraryTo> tos) {
		return tos.stream().map(this::map2Entity).collect(Collectors.toSet());
	}

}
