package pl.edu.pwr.mapper.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pl.edu.pwr.entity.BookLibraryEntity;
import pl.edu.pwr.mapper.Mapper;
import pl.edu.pwr.to.BookLibraryTo;

@Component
public class BookLibraryMapper implements Mapper<BookLibraryTo, BookLibraryEntity> {

	@Override
	public BookLibraryTo map2To(BookLibraryEntity entity) {
		return new BookLibraryTo(entity.getBook(), entity.getLibrary(), entity.getQuantity());
	}

	@Override
	public BookLibraryEntity map2Entity(BookLibraryTo to) {
		return new BookLibraryEntity(to.getBook(), to.getLibrary(), to.getQuantity());
	}

	@Override
	public Set<BookLibraryTo> map2To(Collection<BookLibraryEntity> entities) {
		return entities.stream().map(this::map2To).collect(Collectors.toSet());
	}

	@Override
	public Set<BookLibraryEntity> map2Entity(Collection<BookLibraryTo> tos) {
		return tos.stream().map(this::map2Entity).collect(Collectors.toSet());
	}

}
