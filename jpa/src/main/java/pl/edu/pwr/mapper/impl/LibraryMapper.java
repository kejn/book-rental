package pl.edu.pwr.mapper.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pl.edu.pwr.entity.LibraryEntity;
import pl.edu.pwr.mapper.Mapper;
import pl.edu.pwr.to.LibraryTo;

@Component
public class LibraryMapper implements Mapper<LibraryTo, LibraryEntity> {

	@Override
	public LibraryTo map2To(LibraryEntity entity) {
		return new LibraryTo(entity.getId(), entity.getName());
	}

	@Override
	public LibraryEntity map2Entity(LibraryTo to) {
		return new LibraryEntity(to.getId(), to.getName());
	}

	@Override
	public Set<LibraryTo> map2To(Collection<LibraryEntity> entities) {
		return entities.stream().map(this::map2To).collect(Collectors.toSet());
	}

	@Override
	public Set<LibraryEntity> map2Entity(Collection<LibraryTo> tos) {
		return tos.stream().map(this::map2Entity).collect(Collectors.toSet());
	}

}
