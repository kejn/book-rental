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
	public LibraryTo map2To(LibraryEntity libraryEntity) {
		return new LibraryTo(libraryEntity.getId(), libraryEntity.getName());
	}

	@Override
	public LibraryEntity map2Entity(LibraryTo libraryTo) {
		return new LibraryEntity(libraryTo.getId(), libraryTo.getName());
	}

	@Override
	public Set<LibraryTo> map2To(Collection<LibraryEntity> libraryEntities) {
		return libraryEntities.stream().map(this::map2To).collect(Collectors.toSet());
	}

	@Override
	public Set<LibraryEntity> map2Entity(Collection<LibraryTo> libraryTos) {
		return libraryTos.stream().map(this::map2Entity).collect(Collectors.toSet());
	}

}
